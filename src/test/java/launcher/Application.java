package launcher;

import engine.App;
import model.collada.ColladaLoader;
import model.converter.DAE2Umbi;
import model.converter.OBJ2Umbi;
import model.umbi.UmbiModel;
import model.wavefront.WavefrontLoader;
import model.wavefront.WavefrontModel;
import render.Scene2D;

import java.util.Map;

public class Application {

    public static void main(String[] args) {
//		convertDAE2Umbi("steve.dae");
//		convertOBJ2Umbi("steve_000001.obj");
//		System.exit(0);
        App app = new App("umbi2-engine", 2300, 16 / 9f);
        Scene2D.load();
        app.clearColor(.1f, .1f, .1f);
        app.add(new UmbiLayerLoader("scenes/livello1.umbi2scene"));
        app.add(new ConfigLayer("frames/layerManager.umbi2interface"));
        app.run();
        Scene2D.clean();
    }

    private static void convertDAE2Umbi(String filepath) {
        for (UmbiModel convertModel : DAE2Umbi.convertModels(ColladaLoader.loadScene(filepath))) {
            System.out.println(convertModel.toJSON());
            System.out.println("+------------------------------+");
        }
    }

    private static void convertOBJ2Umbi(String filepath) {
        Map<String, WavefrontModel> models = WavefrontLoader.parseOBJ(filepath);
        for (String modelName : models.keySet()) {
            System.out.println(OBJ2Umbi.convertModel(models.get(modelName)).toJSON());
            System.out.println("+------------------------------+");
        }
    }

}
