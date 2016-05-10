package sut.game01.core.character;


import static playn.core.PlayN.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Image;
import playn.core.*;
import playn.core.Keyboard;
import playn.core.ImageLayer;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.PlayN;
import sut.game01.core.HomeScreen;
import tripleplay.game.Screen;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

import static playn.core.PlayN.graphics;

import sut.game01.core.sprite.*;
import playn.core.util.*;
import sut.game01.core.HomeScreen.*;


public class Mike {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x = 60;
    private int action = 0;
    private int a = 0;
    private World world;
    private Body body;

    public enum State {
        IDLE
    }

    ;

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0;



    public Mike(final World world,final float x_px, final float y_px) {


        sprite = SpriteLoader.getSprite("images/mike2.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px);
                body = initPhysicsBody(world,
                        HomeScreen.M_PER_PIXEL * x_px,
                        HomeScreen.M_PER_PIXEL * y_px);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }

        });

    }

    public Layer layer() {
        return sprite.layer();
    }

    public void update(int delta) {

    }

    public void paint(Clock clock) {
        if(hasLoaded == false)return;

        sprite.layer().setTranslation(
                (body.getPosition().x / HomeScreen.M_PER_PIXEL) +10,
                body.getPosition().y / HomeScreen.M_PER_PIXEL);

    }
    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);//แปลง pixel ให้เป็น m คือ เอา pixel ไปคูณ กับค่าคงที่
        body = world.createBody(bodyDef);
        //bodyDef.active = new Boolean(true);

        //PolygonShape shape = new PolygonShape();
        /*CircleShape shape = new CircleShape();


        shape.setRadius(0.7f);*/
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * HomeScreen.M_PER_PIXEL / 2,
                sprite.layer().height() * HomeScreen.M_PER_PIXEL / 2);

        FixtureDef fixtureDef = new FixtureDef();//น้ำหนัก
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.35f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);
        return body;
    }
}
  



 
