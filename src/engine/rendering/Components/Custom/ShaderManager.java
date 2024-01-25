package engine.rendering.Components.Custom;

import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.Scene;
import engine.rendering.Components.*;
import engine.rendering.Shaders.*;

public class ShaderManager extends Component {
  FragmentShader[] defaultShaders = new FragmentShader[] {
    new DepthShader(),
    new FunkyBackground(),
    new Retical(),
  };
  FragmentShader[] flipside = new FragmentShader[] {
    new DepthShader(),
    new FunkyBackground(),
    new Flip(),
    new InvertColors(),
    new Retical(),
  };
  FragmentShader[] three = new FragmentShader[] {
    new DepthShader(),
    new Blank(),
    new FunkyBackground(),
    new EdgeShader(),
    new Retical(),
  };
  FragmentShader[] four = new FragmentShader[] {
    new DepthShader(),
    new NonFunkyBackground(),
    new Retical(),
    new BayerDither(),
  };  
  FragmentShader[] five = new FragmentShader[] {
    new DepthShader(),
    new FunkyBackground(),
    new Retical(),
    new HueShiftShader(),
    new RotatingInvert(),
  };  
  FragmentShader[] test = new FragmentShader[] {
    new DepthShader(),
    new NonFunkyBackground(),
    new Retical(),
    //new BayerDither(),
  };  
  FragmentShader[] superCrunch = new FragmentShader[] {
    new DepthShader(),
    new FunkyBackground(),
    new DepthShader(),
    new FunkyBackground(),
    new HueShiftShader(),
    new EdgeShader(),
    new BlurShader(),
    new PalletinatorShader(),
    new LowQualityColor(16),
    new Retical(),
  };

  public void start() {
    Scene.setShaders(defaultShaders);
  }

  public void update() {
    if(InputManager.pressed(Keybind.ONE)) {
      Scene.setShaders(defaultShaders);
    }
    if(InputManager.pressed(Keybind.TWO)) {
      Scene.setShaders(flipside);
    }
    if(InputManager.pressed(Keybind.THREE)) {
      Scene.setShaders(three);
    }
    if(InputManager.pressed(Keybind.FOUR)) {
      Scene.setShaders(four);
    }
    if(InputManager.pressed(Keybind.FIVE)) {
      Scene.setShaders(five);
    }
    if(InputManager.pressed(Keybind.SIX)) {
      Scene.setShaders(test);
    }

    if(InputManager.pressed(Keybind.ZERO)) {
      Scene.setShaders(superCrunch);
    }
  }
}
