package sut.game01.core;


import static playn.core.PlayN.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.ImageLayer;

import playn.core.util.Clock;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

import static playn.core.PlayN.graphics;

import sut.game01.core.character.Mike;
import sut.game01.core.sprite.*;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeScreen extends Screen {

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;

    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    private final TestScreen testScreen;
    private final ScreenStack ss;
    private final ImageLayer bg;
    private Mike mike;
    private float x_px,y_px;
    private Sprite sprite;
    private Root root;
    int i=0;
    int j=0;
    public static int aa = 0;
    //ArrayList<Object> o = new ArrayList<Object>();
    public static HashMap<Body, String> bodies = new HashMap<Body, String>();
    ArrayList<Mike> m =  new ArrayList<Mike>();
    private String debugString = String.valueOf(bodies);
   // private Body body;


    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
        Image bgImage = assets().getImage("images/bg.png");
        this.bg = graphics().createImageLayer(bgImage);

        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);


    }

    //=============================================================
    @Override
    public void wasShown() {
        super.wasShown();
        //this.layer.add(bg);
        //mike = new Mike(world,350f,0f);
        m.add(i,new Mike(world,350f,0f));
        //layer.add(m.get(i).layer());
       mouse().setListener(new Mouse.Adapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                /*System.out.println(event.x() + " and " +
                        event.y());*/

                //Mike mike2 = new Mike(world,event.x(),event.y());
                //aa(event.x(),event.y());
                m.add(i,new Mike(world,event.x(),event.y()));
                //m.add(i,mike2)  ;
                layer.add(m.get(i).layer());
                //mike = m.get(i);
                i++;
                j++;;
               /* BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.DYNAMIC;
                bodyDef.position = new Vec2(event.x() * M_PER_PIXEL,
                        event.y() * M_PER_PIXEL);//แปลง pixel ให้เป็น m คือ เอา pixel ไปคูณ กับค่าคงที่
                Body body = world.createBody(bodyDef);
                bodyDef.active = new Boolean(true);

                //PolygonShape shape = new PolygonShape();
               CircleShape shape = new CircleShape();


                shape.setRadius(0.7f);


                FixtureDef fixtureDef = new FixtureDef();//น้ำหนัก
                fixtureDef.shape = shape;
                fixtureDef.density = 0.4f;
                fixtureDef.friction = 0.1f;
                fixtureDef.restitution = 0.35f;

                body.createFixture(fixtureDef);
                body.setLinearDamping(0.2f);
                //body.setTransform(new Vec2(x, y), 0f);
                //return body;*/
                //System.out.println(j);

            }

        });



        if (showDebugDraw) {
            CanvasImage image = graphics().createImage(
                    (int) (width / HomeScreen.M_PER_PIXEL),
                    (int) (height / HomeScreen.M_PER_PIXEL)
            );
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

            debugDraw.setCamera(0, 0, 1f / HomeScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }
        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));
        ground.createFixture(groundShape, 0.0f);
        layer.add(m.get(j).layer());
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                //if(contact.getFixtureA().getBody() == m.get(j).body())
                if(bodies.get(a) != null) {
                    debugString = bodies.get(a) + " contacted with " + bodies.get(b);
                    b.applyForce(new Vec2(1000f, 10f),b.getPosition());
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

    }

   /* public void aa(float x,float y){
        m.add(i,new Mike(world,x,y));

        layer.add(m.get(i).layer());
        //mike = mike2;
        i++;

    }*/


    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        //mike.update(delta);
        for(int k=0;k<=j;k++){
            m.get(k).update(delta);
            //System.out.println(j);
        }

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        //mike.paint(clock);
        for(int k=0;k<=j;k++)
            m.get(k).paint(clock);

        if (showDebugDraw) {
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(debugString, 100,100);
            world.drawDebugData();
        }
    }


}