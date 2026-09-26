// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname;

import static org.junit.Assert.assertEquals;

import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Story;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.security.GenderGuard;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StoryGenderGuardTest {

    private User femaleUser;
    private User maleUser;

    @Before
    public void setUp() {
        femaleUser = new User("f1", "meryem", "Meryem", Gender.FEMALE, null, "");
        maleUser = new User("m1", "ahmed", "Ahmed", Gender.MALE, null, "");
    }

    @Test
    public void filterStories_filtersOppositeGenderStories() {
        List<Story> stories = new ArrayList<>();
        stories.add(new Story("s1", femaleUser, null, "Kadın Hikayesi", ""));
        stories.add(new Story("s2", maleUser, null, "Erkek Hikayesi", ""));

        List<Story> femaleFiltered = GenderGuard.filterStories(femaleUser, stories);
        assertEquals(1, femaleFiltered.size());
        assertEquals(Gender.FEMALE, femaleFiltered.get(0).getUser().getGender());

        List<Story> maleFiltered = GenderGuard.filterStories(maleUser, stories);
        assertEquals(1, maleFiltered.size());
        assertEquals(Gender.MALE, maleFiltered.get(0).getUser().getGender());
    }
}
