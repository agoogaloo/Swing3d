package engine.input;

public class ButtonInput {
    public boolean held = false, pressed = false;
    public int holdTime = 0;


    public void update(){
        if (held){
            pressed = false;
            holdTime+=1;
        }
    }
    public void press(){
        if (held){
            return;
        }
        held = true;
        pressed = true;
    }

    public void release(){
        held = false;
        pressed = false;
        holdTime = 0;
    }
    

    
}
