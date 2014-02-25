package sut.game01.core.sprite;

import debug.DebugDrawBox2D;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.CallbackList;
import playn.core.util.Clock;
import sut.game01.core.TestScreen;
import org.jbox2d.dynamics.World;

import org.jbox2d.dynamics.*;


import javax.swing.plaf.nimbus.State;

public class Kobold {


    private Sprite sprite;
    private int spriteIndex=0;
    private boolean hasLoaded=false;
    private int hp = 100;
    private int t = 0;
    private Body body;

    public Body body() {
        return body;
    }

    public void contact(Contact contact) {
        boolean contacted = true;
        int contactCheck = 0;

        if (state == State.IDLE){
            state = State.DIE;
        }


        if(contact.getFixtureA().getBody() == body){
            Body other = contact.getFixtureB().getBody();
        }else {
            Body other = contact.getFixtureA().getBody();
        }


    }


    public enum State{
        IDLE,ATK,DIE
    };

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);




        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

       return body;
    }

    private State state = State.IDLE;

    private int e =0;
    private int offset=0;
    public Kobold(final World world, final float x_px, final float y_px){

        sprite = SpriteLoader.getSprite("images/kobold.json");
        sprite.addCallback(new CallbackList<Sprite>(){

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px);
                body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x_px, TestScreen.M_PER_PIXEL * y_px);
                hasLoaded = true;

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });




    }



    public Layer layer(){
        return sprite.layer();
    }

    public void update(int delta){
        if(!hasLoaded) return;
        e += delta;


        if(e > 500){
            switch (state){
                case IDLE: offset = 0;
                    break;
                case ATK: offset = 3;
                    break;
                case DIE: offset = 6;
                    if (spriteIndex == 7){
                        sprite.setSprite(spriteIndex);
                    }
                    break;

            }

            spriteIndex = offset + ((spriteIndex + 1)%3);
            sprite.setSprite(spriteIndex);

            e=0;



    }

}

    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation((body.getPosition().x/TestScreen.M_PER_PIXEL)-10,body.getPosition().y/TestScreen.M_PER_PIXEL);
    }
}
