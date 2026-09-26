package com.ummet.mela_rojname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Message;
import com.ummet.mela_rojname.model.Post;
import com.ummet.mela_rojname.model.Reel;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.security.GenderGuard;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenderGuardTest {

    private User femaleUser1;
    private User femaleUser2;
    private User maleUser1;
    private User maleUser2;

    @Before
    public void setUp() {
        femaleUser1 = new User("f1", "zeynep", "Zeynep", Gender.FEMALE, null, "");
        femaleUser2 = new User("f2", "fatma", "Fatma", Gender.FEMALE, null, "");

        maleUser1 = new User("m1", "ahmed", "Ahmed", Gender.MALE, null, "");
        maleUser2 = new User("m2", "hamza", "Hamza", Gender.MALE, null, "");
    }

    @Test
    public void canInteract_sameGender_returnsTrue() {
        assertTrue(GenderGuard.canInteract(femaleUser1, femaleUser2));
        assertTrue(GenderGuard.canInteract(maleUser1, maleUser2));
    }

    @Test
    public void canInteract_crossGender_returnsFalse() {
        assertFalse(GenderGuard.canInteract(femaleUser1, maleUser1));
        assertFalse(GenderGuard.canInteract(maleUser1, femaleUser1));
    }

    @Test
    public void filterPosts_removesOppositeGenderPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("p1", femaleUser1, "Kadın gönderisi", null, 0, 0, ""));
        posts.add(new Post("p2", maleUser1, "Erkek gönderisi", null, 0, 0, ""));

        List<Post> femaleFiltered = GenderGuard.filterPosts(femaleUser1, posts);
        assertEquals(1, femaleFiltered.size());
        assertEquals(Gender.FEMALE, femaleFiltered.get(0).getAuthor().getGender());

        List<Post> maleFiltered = GenderGuard.filterPosts(maleUser1, posts);
        assertEquals(1, maleFiltered.size());
        assertEquals(Gender.MALE, maleFiltered.get(0).getAuthor().getGender());
    }

    @Test
    public void filterReels_removesOppositeGenderReels() {
        List<Reel> reels = new ArrayList<>();
        reels.add(new Reel("r1", femaleUser1, null, "Kadın Reel", 0, ""));
        reels.add(new Reel("r2", maleUser1, null, "Erkek Reel", 0, ""));

        List<Reel> femaleFiltered = GenderGuard.filterReels(femaleUser1, reels);
        assertEquals(1, femaleFiltered.size());
        assertEquals(Gender.FEMALE, femaleFiltered.get(0).getCreator().getGender());
    }

    @Test
    public void filterMessages_removesCrossGenderMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("m1", femaleUser1, femaleUser2, "Aleykümselam", "", true));
        messages.add(new Message("m2", femaleUser1, maleUser1, "Geçersiz mesaj", "", false));

        List<Message> filtered = GenderGuard.filterMessages(femaleUser1, messages);
        assertEquals(1, filtered.size());
    }
}
