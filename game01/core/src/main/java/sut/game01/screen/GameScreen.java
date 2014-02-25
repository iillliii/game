package sut.game01.screen;

import playn.core.Image;
import playn.core.ImageLayer;
import sut.game01.sprite.Kobold;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import static playn.core.PlayN.*;

/**
 * Created by nose on 2/11/14.
 */
public class GameScreen extends Screen {

    Kobold k = new Kobold(560f,400f);
    private ScreenStack ss;

    public GameScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasShown() {
        super.wasShown();
        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bg = graphics().createImageLayer(bgImage);
        //graphics().rootLayer().add(bg);
        //layer.add(bg);


    }

    @Override
    public void wasAdded() {
        super.wasAdded();


        this.layer.add(k.layer());
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        k.update(delta);
    }
}

