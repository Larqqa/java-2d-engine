package program;

import engine.Program;

import static engine.renderer.Renderer.clear;
import static engine.renderer.Renderer.test;

public class PseudoProgram extends Program {
    public PseudoProgram() {
        Program.setTitle("yeet");
        Program.setWidth(300);
//        Program.setScale(1.0);

        start();
    }

    @Override
    public void update() {
    }

    @Override
    public void render(){
        clear();
        test();
    }
}