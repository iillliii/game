package sut.game01.sprite;


import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;

/**
 * Created by nose on 2/11/14.
 */
public class Kobold {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public Layer layer() {
        return sprite.layer();
    }

    public enum State{
        IDLE,ATK,DIE
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;

public Kobold(final float x,final float y){
    sprite = SpriteLoader.getSprite("images/kobold.json");
    sprite.addCallback(new Callback<Sprite>() {
        @Override
        public void onSuccess(Sprite sprite) {
            sprite.setSprite(spriteIndex);
            sprite.layer().setOrigin(sprite.width() /2f,sprite.height() /2f);
            sprite.layer().setTranslation(x,y);
            hasLoaded = true;
        }

        @Override
        public void onFailure(Throwable cause) {
            PlayN.log().error("Error loading image!", cause);
        }
    });

    sprite.layer().addListener(new Pointer.Adapter(){
        @Override
        public void onPointerEnd(Pointer.Event event) {
            super.onPointerEnd(event);
            state = State.ATK;
            spriteIndex = -1;
            e=0;
        }
    });
}
    public void update(int delta){
        if(!hasLoaded) return;
        e+= delta;

        if(e>150){
            switch (state){
                case IDLE: offset = 0;
                    break;
                case ATK: offset=3;
                    break;
                case DIE: offset=6;
                    if (spriteIndex == 7){
                        state = State.DIE;
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex +1)%3);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }

}
