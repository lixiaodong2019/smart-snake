package io.github.guojiank.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class HelpState extends State {
    Texture texture;

    public HelpState(StateManager stateManager) {
        super(stateManager);
        this.texture = new Texture("core/assets/help.jpg");
    }

    void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            stateManager.setState(new GameState(stateManager));
        }
    }

    @Override
    void render(Batch batch) {
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        handleInput();
    }


    @Override
    public void dispose() {
        texture.dispose();
    }
}
