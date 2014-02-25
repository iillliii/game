package sut.game01.core;

import static playn.core.PlayN.*;

import debug.DebugDrawBox2D;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.sprite.Kobold;
import tripleplay.entity.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class TestScreen extends Screen {

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private DebugDrawBox2D debugDraw;
    private Body body2;
    Kobold k;


    private final ScreenStack ss;

    private boolean showDebugDraw = true;


    public TestScreen(ScreenStack ss) {
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity, true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        k = new Kobold(world,300f,395f);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getBody()== k.body()||
                        contact.getFixtureB().getBody() == k.body()){
                    k.contact(contact);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });



        createBox2();
        super.wasAdded();



        Image bgImage = assets().getImage("images/bg.png");
        Image backImage = assets().getImage("images/play.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });


        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / TestScreen.M_PER_PIXEL),
                    (int) (height / TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                               DebugDraw.e_jointBit |
                               DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(0, height-2), new Vec2(width, height-2));
        ground.createFixture(groundShape, 0.0f);

        ImageLayer play = graphics().createImageLayer(backImage);
        layer.add(play);

        play.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                body2.applyLinearImpulse(new Vec2(100f,0f),body2.getPosition());
            }
        });

        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        //layer.add(bgLayer);

        layer.add(k.layer());


    }




    private void createBox2(){
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0, 0);

        body2 = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 0f;
        body2.createFixture(fd);
        body2.setLinearDamping(0.5f);
        body2.setTransform(new Vec2(5f, height-2), 0);

    }



    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        k.update(delta);
    }
    @Override
    public void paint(Clock clock) {
        super.paint(clock);

        k.paint(clock);
        if (showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
