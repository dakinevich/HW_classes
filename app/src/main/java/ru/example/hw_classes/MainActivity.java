package ru.example.hw_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.example.hw_classes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        class Cargo{
            public int amount;
            public Cargo(int a){
                this.amount = a;
                update_text();
            }
            public void drop_cargo(){
                this.amount-=1;
                update_text();
            }
            public void update_text(){
                binding.cargo.setText("Груз: " + this.amount);
            }

        }
        class Motor{
            public int speed;
            public int state;
            public Motor(int speed){
                this.state = 100;
                this.speed = speed;
                update_text();
            }
            public void use_motor(){
                this.state -= 5;
                update_text();
            }
            public void repair_motor(){
                this.state += 20;
                this.speed += 5;
                if (this.state>100){
                    this.state = 100;
                }
                update_text();
            }
            public void update_text(){
                binding.motorState.setText("Состояние мотора " + this.state + "%");
                binding.speed.setText("Скорость погружения " + this.speed + "м");
            }
        }
        class Commander{
            public int significance;
        }
        class Crew{
            public int mental_state;
            public Crew(){
                this.mental_state = 100;
                this.update_text();
            }
            public void use_crew(){
                this.mental_state-=5;
                this.update_text();
            }
            public void relax_crew(){
                this.mental_state+=20;
                if (this.mental_state>100){
                    this.mental_state = 100;
                }
                this.update_text();
            }
            public void update_text(){
                binding.crewState.setText("Состояние экипажа " + this.mental_state + "%");
            }
        }

        class Boat{
            public int state;
            public int height;
            private Motor motor;
            private Cargo cargo;
            private Crew crew;
            private Commander commander;
            public Boat(){
                this.state = 100;
                this.height = 100;
                this.motor = new Motor(5);
                this.cargo = new Cargo(5);
                this.crew = new Crew();
                this.commander = new Commander();
                this.update_text();
                binding.ibWater.setBackgroundColor(Color.rgb(128 + height,128 + height, 128 + height));
            }
            public void move_down(){
                this.motor.use_motor();
                this.use_boat();
                this.height -= motor.speed;
                this.update_text();
            }
            public void move_up(){
                this.motor.use_motor();
                this.use_boat();
                this.height += motor.speed;
                this.update_text();
            }
            public void update(){
                this.motor.repair_motor();
                this.use_boat();
                this.update_text();
            }
            public void repair(){
                this.use_boat();
                this.state += 20;
                if (this.state>100){
                    this.state = 100;
                }
                this.update_text();
            }
            public void relax(){
                this.crew.relax_crew();
            }
            public void dropCargo(){
                this.crew.use_crew();
                this.cargo.drop_cargo();
                this.height -= 10;
            }
            public void use_boat(){
                this.state -= 5;
                this.crew.use_crew();
                this.update_text();
            }
            public void update_text(){
                binding.boatState.setText("Состояние лодки " + this.state + "%");
                binding.height.setText("Глубина " + this.height + "м");
            }
        }
        class Game{
            public int progress;
            public Boat boat;
            public Game(){
                this.boat = new Boat();
            }
            public void click_down(){
                boat.move_down();
                binding.ibWater.setBackgroundColor(Color.rgb(128 + boat.height,128 + boat.height, 128 + boat.height));
                check_game();
            }
            public void click_up(){
                boat.move_up();
                binding.ibWater.setBackgroundColor(Color.rgb(128 + boat.height,128 + boat.height, 128 + boat.height));
                check_game();
            }
            public void click_update(){
                boat.update();
                check_game();
            }
            public void click_repair(){
                boat.repair();
                check_game();
            }
            public void click_relax(){
                boat.relax();
                check_game();
            }
            public void click_dropCargo(){
                boat.dropCargo();
                check_game();
            }
            public void check_game(){
                if (this.boat.state<=0 | this.boat.cargo.amount <= 0 | this.boat.motor.state <= 0 | this.boat.crew.mental_state<=0){
                    Toast.makeText(getApplicationContext(),"Game over!", Toast.LENGTH_SHORT).show();
                    this.boat = new Boat();
                }
                else if (this.boat.height <=0){
                    Toast.makeText(getApplicationContext(),"You win! with score: "+this.boat.cargo.amount, Toast.LENGTH_SHORT).show();
                    this.boat = new Boat();
                }
            }
        }

        Game game = new Game();

        binding.down.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_down();
            }
        });
        binding.up.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_up();
            }
        });
        binding.update.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_update();
            }
        });
        binding.repair.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_repair();
            }
        });
        binding.relax.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_relax();
            }
        });
        binding.drop.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view){
                game.click_dropCargo();
            }
        });
    }
}