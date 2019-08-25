package MiscTest;

import org.joor.Reflect;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.function.Supplier;

public class CompilerTest {

    public static String someString = "Cool boy";

    public static void main(String[] args) throws Exception {

        File classToRead = new File( "C:/Users/a1ste/IdeaProjects/GUI/GUIModule/src/main/java/MiscTest/TestClass.java" );
        System.out.println( classToRead.exists() );
        Thread.sleep( 20000 );
        System.out.println( classToRead.exists() );

        String file = "";
        BufferedReader br = new BufferedReader( new FileReader( classToRead ) );
        String line;
        while ( (line = br.readLine() ) != null) {
            file += line;
        }

        Reflect ref = Reflect.compile( "MiscTest.TestClass", file );
        ref.create();

    }

}