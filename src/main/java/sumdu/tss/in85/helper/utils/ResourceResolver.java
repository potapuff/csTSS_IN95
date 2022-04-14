package sumdu.tss.in85.helper.utils;

import java.io.File;

/**
 * Class provide possibility find resource both in local filesystem and class resources
 */
public class ResourceResolver {

    public static File getResource(String name) {
        var resource = ClassLoader.getSystemResource(name);  // A system class.
        if (resource != null) {
            return new File(resource.getFile());
        }
        var file = new File(name);
        if (file.exists()) {
            return file;
        }
        throw new RuntimeException("File " + name + " not found");
    }
}
