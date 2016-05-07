package sut.game01.core;


import static playn.core.PlayN.*;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
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


public class HomeScreen extends Screen  {

    public static float M_PER_PIXEL =1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;

    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

  private final TestScreen testScreen;
  private final ScreenStack ss;
  private final ImageLayer bg;

  private Root root;

  public HomeScreen(final ScreenStack ss){
    this.ss = ss;
    this.testScreen =new TestScreen(ss);
    Image bgImage = assets().getImage("images/bg.png");
    this.bg = graphics().createImageLayer(bgImage);

      Vec2 gravity = new Vec2(0.0f,10.0f);
      world = new World(gravity);
      world.setWarmStarting(true);
      world.setAutoClearForces(true);


  }

  //=============================================================
  @Override
  public void wasShown (){
    super.wasShown();
    this.layer.add(bg);
      mouse().setListener(new Mouse.Adapter(){
          @Override
          public void onMouseDown(Mouse.ButtonEvent event) {
              BodyDef bodyDef = new BodyDef();
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
              //return body;
          }
      });

      if(showDebugDraw) {
          CanvasImage image = graphics().createImage(
                  (int) (width / HomeScreen.M_PER_PIXEL),
                  (int) (height / HomeScreen.M_PER_PIXEL )
          );
          layer.add(graphics().createImageLayer(image));
          debugDraw = new DebugDrawBox2D();
          debugDraw.setCanvas(image);
          debugDraw.setFlipY(false);
          debugDraw.setStrokeAlpha(150);
          debugDraw.setFillAlpha(75);
          debugDraw.setStrokeWidth(2.0f);
          debugDraw.setFlags(DebugDraw.e_shapeBit|
                  DebugDraw.e_jointBit|
                  DebugDraw.e_aabbBit);

          debugDraw.setCamera(0,0,1f / HomeScreen.M_PER_PIXEL);
          world.setDebugDraw(debugDraw);
      }
      Body ground = world.createBody(new BodyDef());
      EdgeShape groundShape = new EdgeShape();
      groundShape.set(new Vec2(0,height), new Vec2(width, height));
      ground.createFixture(groundShape, 0.0f);

  }
    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }


}