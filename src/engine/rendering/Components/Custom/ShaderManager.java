package engine.rendering.Components.Custom;

import java.awt.Color;

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
  FragmentShader[] farsighted = new FragmentShader[] {
    new DepthShader(true),
    new FunkyBackground(),
    new BlurShader(),
    new Retical(),
  };
  FragmentShader[] four = new FragmentShader[] {
    new DepthShader(),
    new NonFunkyBackground(),
    new EdgeShader(new Color(147, 255, 248)),
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
  FragmentShader[] mint = new FragmentShader[] {
    new DepthShader(),
    new PalletinatorShader(),
    new InvertColors(),
    new Retical(),
  };
  FragmentShader[] seven = new FragmentShader[] {
    new FunkyBackground(),
    new RandomNoise(),
    new PalletinatorShader(),
    new BlurShader(),
    new EdgeShader(new Color(0, 255, 200)),
    new DepthShader(),
    new FunkyBackground(),
    new Retical(),
  };
  FragmentShader[] asdfsafkjshdfkljsaf = new FragmentShader[] {
    new TVStatic(),
    new RandomNoise(),
    new PalletinatorShader(),
    new RotatingInvert(),
    new BayerDither(),
    new Retical(),
  };
  FragmentShader[] nine = new FragmentShader[] {
    new DepthShader(),
    new FunkyBackground(),
    new HueShiftShader(),
    new LowQualityColor(8),
    new BlurShader(),
    new Retical(),
  };
 
  FragmentShader[] superCrunch = new FragmentShader[] {
    new EdgeShader(),
    new DepthShader(),
    new FunkyBackground(),
    new BayerDither(),
    new PalletinatorShader(),
    new HueShiftShader(),
    new BlurShader(),
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
      Scene.setShaders(farsighted);
    }
    if(InputManager.pressed(Keybind.FOUR)) {
      Scene.setShaders(four);
    }
    if(InputManager.pressed(Keybind.FIVE)) {
      Scene.setShaders(five);
    }
    if(InputManager.pressed(Keybind.SIX)) {
      Scene.setShaders(mint);
    }
    if(InputManager.pressed(Keybind.SEVEN)) {
      Scene.setShaders(seven);
    }
    if(InputManager.pressed(Keybind.EIGHT)) {
      Scene.setShaders(asdfsafkjshdfkljsaf);
    }
    if(InputManager.pressed(Keybind.NINE)) {
      Scene.setShaders(nine);
    }
    if(InputManager.pressed(Keybind.ZERO)) {
      Scene.setShaders(superCrunch);
    }
  }
}
