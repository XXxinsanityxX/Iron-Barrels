package insanity.mods.ironbarrels.client;

import org.lwjgl.opengl.GL11;

public class OpenGLHelper {

    private static void disable() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void disableBlend() {
        disable();
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }
}