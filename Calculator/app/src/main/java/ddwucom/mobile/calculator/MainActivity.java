package ddwucom.mobile.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LayoutInflater inflater;

    LinearLayout scientific;
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

    final int NOTHING = -1;
    final int DIVISON = 0;
    final int MULTI = 1;
    final int MINUS = 2;
    final int PLUS = 3;
    final int POWER = 4;
    final int ROOT = 5;
    final int SIN = 6;

    int sign = NOTHING;
    boolean isFirst = true;

    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_main);

        scientific_layout = findViewById(R.id.scientific_layout);
        scientific = (LinearLayout)inflater.inflate(R.layout.scientific_calculator, scientific_layout);

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

        btnDivide.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.general:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    scientific_layout.setVisibility(View.INVISIBLE);
                    item.setChecked(true);
                }
                break;
            case R.id.scientific_layout:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    scientific_layout.setVisibility(View.VISIBLE);
                    item.setChecked(true);
                }
                break;
        }
        return true;
    }

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

    View.OnClickListener equalClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (sign==NOTHING || edtAnswer.getText().toString().equals("") && sign!=SIN && sign!=ROOT){
                Toast.makeText(MainActivity.this, "순서가 잘못되었습니다. 다시 입력하세요", Toast.LENGTH_SHORT).show();
                edtAnswer.setText("");
                edtAnswer.setHint("");
                isFirst = true;
                result = 0;
            }
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
