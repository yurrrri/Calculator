### 계산기
![슬라이드1](https://user-images.githubusercontent.com/37764504/84234293-ececab00-ab2e-11ea-8d80-026afde2da4b.PNG)
![슬라이드2](https://user-images.githubusercontent.com/37764504/84234297-ee1dd800-ab2e-11ea-9a1e-7a28a38a64d4.PNG)
![슬라이드3](https://user-images.githubusercontent.com/37764504/84234299-ee1dd800-ab2e-11ea-8432-e6dcce513df1.PNG)
![슬라이드4](https://user-images.githubusercontent.com/37764504/84234301-eeb66e80-ab2e-11ea-9fac-04f7e37ce832.PNG)
![슬라이드5](https://user-images.githubusercontent.com/37764504/84234302-ef4f0500-ab2e-11ea-9a86-e1daad77d9ec.PNG)

+ **모든 계산은 연속 계산이 가능함** (예:45+5/2*4 = 100, 연산자의 우선순위는 고려하지 않았음)

## 화면구성
### 메인 화면

**activity_main.xml**

![basic_calculator_layout](https://user-images.githubusercontent.com/37764504/84234919-004c4600-ab30-11ea-9fc3-075390c36e36.PNG)

- 숫자 입력칸 : EditText
- 숫자, 연산자, 등호 : Button

**scientific_calculator.xml**

![scientific_calculator_layout](https://user-images.githubusercontent.com/37764504/84234923-017d7300-ab30-11ea-8ace-56fd9ffc698f.PNG)

- 연산자 : Button

공학용 계산기에 추가되는 버튼 레이아웃을 따로 생성하였음
일반계산기에서 사용자가 옵션을 공학용으로 선택하면 일반용 계산기 버튼 아래에 있는 빈 레이아웃에 버튼만 따로 추가되도록 하였음

### 옵션 메뉴

**res/menu/menu_option.xml**

![option_menu](https://user-images.githubusercontent.com/37764504/84235235-781a7080-ab30-11ea-8a92-36a22c8583a2.PNG)

- 옵션 : 라디오 버튼

### 액션바 테마

**res/values/styles.xml**

```xml
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
```

### 연산자 버튼 디자인

**drawable/button.xml**

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle" android:padding = "10dp">
    <solid android:color="#fdd835"></solid>
    <corners
        android:bottomLeftRadius="15dp"
        android:bottomRightRadius="15dp"
        android:topLeftRadius="15dp"
        android:topRightRadius="15dp"></corners>
</shape>
```

## 기능 구현

**MainActivity.java**

```java
---중략---
public class MainActivity extends AppCompatActivity {
    LayoutInflater inflater; //공학용 계산기의 버튼 부분을 인플레이션 하기 위한 인플레이터

    LinearLayout scientific; //계산기 각각의 레이아웃 준비
    LinearLayout scientific_layout;

    EditText edtAnswer;
    Button btnDivide;
    Button btnMultiply;
    Button btnPlus;
    Button btnMinus;
    Button btnClear;
    Button btnEqual;
    Button btnPower;
    Button btnRoot;
    Button btnSin;

    final int NOTHING = -1; //연산자를 구분하기 위한 상수 정의
    final int DIVISON = 0;
    final int MULTI = 1;
    final int MINUS = 2;
    final int PLUS = 3;
    final int POWER = 4;
    final int ROOT = 5;
    final int SIN = 6;

    int sign = NOTHING; //초기 상태를 연산자가 없는 상태로 정의하기 위해
    boolean isFirst = true; //맨 처음 숫자를 입력하는 것인지 판단하기 위한 플래그 변수

    double result; //연산 결과 저장 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_main);

        scientific_layout = findViewById(R.id.scientific_layout);
        //공학용 계산기 버튼 레이아웃 인플레이션
        scientific = (LinearLayout)inflater.inflate(R.layout.scientific_calculator, scientific_layout);

        //초기에 안보이게 설정 (일반용 계산기가 초기화면)
        scientific_layout.setVisibility(View.INVISIBLE);

        edtAnswer = findViewById(R.id.edtAnswer);
        btnDivide = findViewById(R.id.btnDivide);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnClear = findViewById(R.id.btnClear);
        btnPower = findViewById(R.id.btnPower);
        btnRoot = findViewById(R.id.btnRoot);
        btnSin = findViewById(R.id.btnSin);
        btnEqual = findViewById(R.id.btnEqual);

        //각각의 연산자 버튼마다 ClickListener 구현
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //숫자 버튼보다 연산자 버튼이 먼저 왔을 경우 : EditText에 아무것도 입력 X
                //숫자를 다시 입력할 수 있도록 Toast 안내 
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                //연속 계산을 구현하기 위해 만든 코드. 맨 처음에 입력한 숫자일 경우 맨 처음 숫자를 result 변수에 저장해두어야함
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                //isFirst가 아니다 -> 숫자가 처음 온 것이 아니므로 연산자 경우마다 계산해서 result에 저장해야함
                //연산자마다 각기 다른 계산 수행 후 result에 저장
                } else if (sign == DIVISON) {
                 //0으로 나눌 수 없으므로 Toast 안내 후 다시 입력할 수 있도록 구현
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = DIVISON;
                edtAnswer.setText("");
                edtAnswer.setHint("/");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = MULTI;
                edtAnswer.setText("");
                edtAnswer.setHint("*");
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = PLUS;
                edtAnswer.setText("");
                edtAnswer.setHint("+");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = MINUS;
                edtAnswer.setText("");
                edtAnswer.setHint("-");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAnswer.setText("");
                edtAnswer.setHint("");
                sign = NOTHING;
                isFirst = true;
                result = 0;
            }
        });

        btnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = ROOT;
                edtAnswer.setText("");
                edtAnswer.setHint("root");
            }
        });

        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = SIN;
                edtAnswer.setText("");
                edtAnswer.setHint("sin");
            }
        });

        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAnswer.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (isFirst) {
                    result = Double.parseDouble(edtAnswer.getText().toString());
                    isFirst = false;
                } else if (sign == DIVISON) {
                    if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                        Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                        edtAnswer.setText("");
                    }
                    else
                        result /= Double.parseDouble(edtAnswer.getText().toString());
                }
                else if (sign==MULTI)
                    result*=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==MINUS)
                    result-=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==PLUS)
                    result+=Double.parseDouble(edtAnswer.getText().toString());
                else if (sign==POWER)
                    result=Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                else if (sign==SIN)
                    result=Math.sin(Math.toRadians(result));
                else if (sign==ROOT)
                    result = Math.sqrt(result);
                sign  = POWER;
                edtAnswer.setText("");
                edtAnswer.setHint("power");
            }
        });
        btnEqual.setOnClickListener(equalClick);
    }

    //일반용 <-> 공학용 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }
   
    //일반용, 공학용 옵션을 각각 선택했을 경우
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.general:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    //일반용 옵션을 선택했으면 공학용 계산기 버튼이 보이지 않도록 설정
                    scientific_layout.setVisibility(View.INVISIBLE);
                    //해당 아이템이 체크된 상태로 설정
                    item.setChecked(true);
                }
                break;
            case R.id.scientific_layout:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    //공학용 옵션을 선택했으면 공학용 계산기 버튼이 보이도록 설정
                    scientific_layout.setVisibility(View.VISIBLE);
                     //해당 아이템이 체크된 상태로 설정
                    item.setChecked(true);
                }
                break;
        }
        return true;
    }

    //숫자 버튼을 누를때마다 숫자가 추가되어 편집창(EditText)에 보여지게 구현
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                edtAnswer.setText(edtAnswer.getText().toString()+0);
                break;
            case R.id.btn1:
                edtAnswer.setText(edtAnswer.getText().toString()+1);
                break;
            case R.id.btn2:
                edtAnswer.setText(edtAnswer.getText().toString()+2);
                break;
            case R.id.btn3:
                edtAnswer.setText(edtAnswer.getText().toString()+3);
                break;
            case R.id.btn4:
                edtAnswer.setText(edtAnswer.getText().toString()+4);
                break;
            case R.id.btn5:
                edtAnswer.setText(edtAnswer.getText().toString()+5);
                break;
            case R.id.btn6:
                edtAnswer.setText(edtAnswer.getText().toString()+6);
                break;
            case R.id.btn7:
                edtAnswer.setText(edtAnswer.getText().toString()+7);
                break;
            case R.id.btn8:
                edtAnswer.setText(edtAnswer.getText().toString()+8);
                break;
            case R.id.btn9:
                edtAnswer.setText(edtAnswer.getText().toString()+9);
                break;
        }
    }

    //등호 버튼 기능 구현 
    View.OnClickListener equalClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //연산자 없이 먼저 등호버튼이 왔거나, 숫자가 오기전에 등호버튼이 왔을 경우 Toast 안내 및 값 초기화
            if (sign==NOTHING || edtAnswer.getText().toString().equals("") && sign!=SIN && sign!=ROOT){
                Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                edtAnswer.setText("");
                edtAnswer.setHint("");
                isFirst = true;
                result = 0;
            }
            //0으로 나눴을 때 Toast 안내 후 값 다시 입력하도록 구현
            //결과를 편집창(EditText)에 표시 후 연산자 초기화 (sign=NOTHING)
            else if (sign == DIVISON) {
                if (Double.parseDouble(edtAnswer.getText().toString()) == 0) {
                    Toast.makeText(MainActivity.this, "0으로 나눌 수 없습니다. 수를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                    edtAnswer.setText("");
                } else {
                    result /= Double.parseDouble(edtAnswer.getText().toString());
                    edtAnswer.setText(Double.toString(result));
                    sign = NOTHING;
                }
            }
            else if (sign==MULTI){
                result*=Double.parseDouble(edtAnswer.getText().toString());
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            else if (sign==MINUS) {
                result -= Double.parseDouble(edtAnswer.getText().toString());
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            else if (sign==PLUS) {
                result += Double.parseDouble(edtAnswer.getText().toString());
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            else if (sign==POWER) {
                result = Math.pow(result, Double.parseDouble(edtAnswer.getText().toString()));
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            else if (sign==SIN) {
                result = Math.sin(Math.toRadians(result));
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            else if (sign==ROOT) {
                result = Math.sqrt(result);
                edtAnswer.setText(Double.toString(result));
                sign = NOTHING;
            }
            }
        };
}

```
