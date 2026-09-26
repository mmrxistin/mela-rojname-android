// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.security;

import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Message;
import com.ummet.mela_rojname.model.Post;
import com.ummet.mela_rojname.model.Reel;
import com.ummet.mela_rojname.model.User;

import java.util.ArrayList;
import java.util.List;

public class GenderGuard {

    /**
     * Checks if two users have the same gender.
     * Sharia-compliant social media rules strictly forbid cross-gender direct interaction.
     */
    public static boolean canInteract(User currentUser, User targetUser) {
        if (currentUser == null || targetUser == null) return false;
        Gender currentGender = currentUser.getGender();
        Gender targetGender = targetUser.getGender();
        return currentGender != null && currentGender == targetGender;
    }

    /**
     * Filters list of posts to return ONLY posts written by users of the SAME gender.
     */
    public static List<Post> filterPosts(User currentUser, List<Post> posts) {
        List<Post> filtered = new ArrayList<>();
        if (currentUser == null || posts == null) return filtered;

        for (Post post : posts) {
            if (post.getAuthor() != null && canInteract(currentUser, post.getAuthor())) {
                filtered.add(post);
            }
        }
        return filtered;
    }

    /**
     * Filters list of reels to return ONLY reels created by users of the SAME gender.
     */
    public static List<Reel> filterReels(User currentUser, List<Reel> reels) {
        List<Reel> filtered = new ArrayList<>();
        if (currentUser == null || reels == null) return filtered;

        for (Reel reel : reels) {
            if (reel.getCreator() != null && canInteract(currentUser, reel.getCreator())) {
                filtered.add(reel);
            }
        }
        return filtered;
    }

    /**
     * Filters list of users to return ONLY users of the SAME gender.
     */
    public static List<User> filterUsers(User currentUser, List<User> users) {
        List<User> filtered = new ArrayList<>();
        if (currentUser == null || users == null) return filtered;

        for (User user : users) {
            if (canInteract(currentUser, user)) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    /**
     * Filters list of messages to ensure no cross-gender messages are displayed.
     */
    public static List<Message> filterMessages(User currentUser, List<Message> messages) {
        List<Message> filtered = new ArrayList<>();
        if (currentUser == null || messages == null) return filtered;

        for (Message msg : messages) {
            if (msg.getSender() != null && msg.getRecipient() != null) {
                if (canInteract(currentUser, msg.getSender()) && canInteract(currentUser, msg.getRecipient())) {
                    filtered.add(msg);
                }
            }
        }
        return filtered;
    }
}
