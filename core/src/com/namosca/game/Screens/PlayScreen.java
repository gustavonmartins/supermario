package com.namosca.game.Screens;

// Next video> https://www.youtube.com/watch?v=a8MPxzkwBwo&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt part 9

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.namosca.game.MarioBros;
import com.namosca.game.Scenes.Hud;
import com.namosca.game.Sprites.Mario;
import com.namosca.game.Tools.B2WorldCreator;
import com.namosca.game.Tools.WorldContactListener;

public class PlayScreen implements Screen {
	private MarioBros game;
	private TextureAtlas atlas;
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Mario player;
	
	
	public PlayScreen(MarioBros game) {
		atlas = new TextureAtlas("resources/Mario_and_Enemies.pack");
		this.game=game;
		gamecam=new OrthographicCamera();
		gamePort = new FitViewport(MarioBros.V_WIDTH/MarioBros.PPM,MarioBros.V_HEIGHT/MarioBros.PPM,gamecam);
		hud=new Hud(game.batch);
		
		maploader = new TmxMapLoader();
		map = maploader.load("resources/level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map,1/MarioBros.PPM);
		gamecam.position.set(gamePort.getScreenWidth()/2,gamePort.getScreenHeight()/2,0);
		
		world = new World(new Vector2(0,-10), true);
		b2dr = new Box2DDebugRenderer();
		
		
		new B2WorldCreator(world, map);
		
		player= new Mario(world, this);
		
		world.setContactListener(new WorldContactListener());
		
		
		
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void handleInput(float dt) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&  (player.b2body.getLinearVelocity().x<=2))) {
			player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) &&  (player.b2body.getLinearVelocity().x>=-2))) {
			player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
		}
		
	}
	
	public void update(float dt) {
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		player.update(dt);
		
		hud.update(dt);
		
		gamecam.position.x=player.b2body.getPosition().x;
		
		gamecam.update();
		renderer.setView(gamecam);
		
	}

	@Override
	public void render(float delta) {
		update (delta);
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		
		
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();

	}

}
