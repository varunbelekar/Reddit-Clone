package examples.posts;

import com.intuit.karate.junit5.Karate;

public class PostRunner {

    @Karate.Test
    Karate testPosts(){
        return Karate.run("posts").relativeTo(getClass());
    }
}
