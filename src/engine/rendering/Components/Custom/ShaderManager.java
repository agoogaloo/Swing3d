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
  FragmentShader[] six = new FragmentShader[] {
    new DepthShader(),
    new PalletinatorShader(),
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
  // FragmentShader[] nine = new FragmentShader[] {
  //   new TVStatic(),
  //   new RandomNoise(),
  //   new PalletinatorShader(),
  //   new RotatingInvert(),
  //   new BayerDither(),
  //   new Retical(),
  // };
 
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
    new BayerDither(),
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
      Scene.setShaders(six);
    }
    if(InputManager.pressed(Keybind.SEVEN)) {
      Scene.setShaders(seven);
    }
    if(InputManager.pressed(Keybind.EIGHT)) {
      Scene.setShaders(asdfsafkjshdfkljsaf);
    }

    if(InputManager.pressed(Keybind.ZERO)) {
      Scene.setShaders(superCrunch);
    }
  }
}
