package advance.codeStructure;

import java.util.LinkedList;
import java.util.List;

public class SourceCode {

    private String fileName;
    private List<Method> methods;
    private List<String> comments;

    public SourceCode() {
        methods = new LinkedList<>();
        comments = new LinkedList<>();
    }

    public SourceCode(List<Method> methods) {
        this.methods = methods;
        comments = new LinkedList<>();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<String> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "SourceCode \n" +
                "  methods: \n" + methods +
                "\n comments : \n" + comments;
    }
}
