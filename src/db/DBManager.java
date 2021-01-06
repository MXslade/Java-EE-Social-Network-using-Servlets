package db;

import models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/task5_db", "postgres", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(User user) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO users (user_email, user_password, user_full_name, user_birth_date) " +
                    "VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setDate(4, user.getBirthdate());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static User getUserByEmail(String email) {
        User user = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM users WHERE user_email = ?;");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_full_name"),
                        resultSet.getDate("user_birth_date"),
                        resultSet.getString("user_picture_url")
                );
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getUserById(Long id) {
        User user = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM users WHERE user_id = ?;");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_full_name"),
                        resultSet.getDate("user_birth_date"),
                        resultSet.getString("user_picture_url")
                );
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean updateGeneralInformationOfUser(User user) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE users SET user_full_name = ?, user_birth_date = ? WHERE user_id = ?;");
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setDate(2, user.getBirthdate());
            preparedStatement.setLong(3, user.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updateProfilePictureOfUser(User user) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE users SET user_picture_url = ? WHERE user_id = ?;");
            preparedStatement.setString(1, user.getPictureUrl());
            preparedStatement.setLong(2, user.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updatePasswordOfUser(User user) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE users SET user_password = ? WHERE user_id = ?;");
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setLong(2, user.getId());

            rows  = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean addPost(Post post) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO posts (author_id, post_title, post_short_content, post_content, post_date) " +
                    "VALUES (?, ?, ?, ?, now());");
            preparedStatement.setLong(1, post.getAuthor().getId());
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getShortContent());
            preparedStatement.setString(4, post.getContent());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM posts " +
                    "INNER JOIN users ON posts.author_id = users.user_id " +
                    "ORDER BY posts.post_date DESC;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                posts.add(new Post(
                        resultSet.getLong("post_id"),
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getString("user_full_name"),
                                resultSet.getDate("user_birth_date"),
                                resultSet.getString("user_picture_url")
                        ),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_short_content"),
                        resultSet.getString("post_content"),
                        resultSet.getTimestamp("post_date")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static List<Post> getAllUserPostsById(Long userId) {
        List<Post> posts = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM posts " +
                    "INNER JOIN users ON posts.author_id = users.user_id " +
                    "WHERE posts.author_id = ? " +
                    "ORDER BY posts.post_date DESC;");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                posts.add(new Post(
                        resultSet.getLong("post_id"),
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getString("user_full_name"),
                                resultSet.getDate("user_birth_date"),
                                resultSet.getString("user_picture_url")
                        ),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_short_content"),
                        resultSet.getString("post_content"),
                        resultSet.getTimestamp("post_date")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static boolean updatePost(Post post) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE posts SET post_title = ?, post_short_content = ?, post_content = ? " +
                    "WHERE post_id = ?;");
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getShortContent());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setLong(4, post.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static Post getPostById(Long id) {
        Post post = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM posts " +
                    "INNER JOIN users ON posts.author_id = users.user_id " +
                    "WHERE posts.post_id = ?;");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                post = new Post(
                        resultSet.getLong("post_id"),
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getString("user_full_name"),
                                resultSet.getDate("user_birth_date"),
                                resultSet.getString("user_picture_url")
                        ),
                        resultSet.getString("post_title"),
                        resultSet.getString("post_short_content"),
                        resultSet.getString("post_content"),
                        resultSet.getTimestamp("post_date")
                );
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }

    public static boolean deletePost(Long id) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM posts WHERE post_id = ?;");
            preparedStatement.setLong(1, id);

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static List<Friend> getAllUserFriends(User user) {
        List<Friend> friends = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM friends " +
                    "INNER JOIN users on friends.friend_secondary_id = users.user_id " +
                    "WHERE friends.friend_main_id = ?;");
            preparedStatement.setLong(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                friends.add(new Friend(
                        resultSet.getLong("friend_id"),
                        user,
                        new User(
                                resultSet.getLong("user_id"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getString("user_full_name"),
                                resultSet.getDate("user_birth_date"),
                                resultSet.getString("user_picture_url")
                        ),
                        resultSet.getTimestamp("friend_added_time")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return friends;
    }

    public static List<User> findUsersByFullNames(String fullName) {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM users WHERE user_full_name LIKE ?;");
            preparedStatement.setString(1, fullName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_full_name"),
                        resultSet.getDate("user_birth_date"),
                        resultSet.getString("user_picture_url")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<Long> getAllFriendRequestReceiversOfUser(User user) {
        List<Long> ids = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM friends_requests WHERE friend_request_sender_id = ?;");
            preparedStatement.setLong(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ids.add(resultSet.getLong("friend_request_receiver_id"));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    public static boolean addFriendRequest(User senderUser, User receiverUser) {
        int rows = 0;

        try {
            if (friendRequestAlreadyExists(senderUser, receiverUser)) {
                return false;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO friends_requests (friend_request_receiver_id, friend_request_sender_id, friend_request_sent_time) " +
                    "VALUES (?, ?, now());");
            preparedStatement.setLong(1, receiverUser.getId());
            preparedStatement.setLong(2, senderUser.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static List<Long> getAllFriendRequestSendersOfUser(User user) {
        List<Long> ids = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM friends_requests WHERE friend_request_receiver_id = ?;");
            preparedStatement.setLong(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ids.add(resultSet.getLong("friend_request_sender_id"));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    public static boolean confirmFriendRequest(User receiverUser, User senderUser) {
        int rows = 0;

        try {
            if (friendRequestAlreadyExists(senderUser, receiverUser)) {
                PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "INSERT INTO friends (friend_main_id, friend_secondary_id, friend_added_time) " +
                        "VALUES (?, ?, now())");
                preparedStatement.setLong(1, senderUser.getId());
                preparedStatement.setLong(2, receiverUser.getId());

                rows += preparedStatement.executeUpdate();

                preparedStatement.setLong(1, receiverUser.getId());
                preparedStatement.setLong(2, senderUser.getId());

                rows += preparedStatement.executeUpdate();

                preparedStatement.close();
                System.out.println("Friend request was deleted: " + deleteFriendRequest(senderUser, receiverUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static Chat getChat(User user, User user2) {
        Chat chat = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM chats " +
                    "WHERE (chat_user_id = ? AND chat_opponent_user_id = ?) " +
                    "OR (chat_user_id = ? AND chat_opponent_user_id = ?)");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user2.getId());
            preparedStatement.setLong(3, user2.getId());
            preparedStatement.setLong(4, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chat = new Chat(
                        resultSet.getLong("chat_id"),
                        user,
                        user2,
                        resultSet.getTimestamp("chat_created_date"),
                        resultSet.getString("chat_latest_message_text"),
                        resultSet.getTimestamp("chat_latest_message_time")
                );
            } else {
                createChat(user, user2);
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chat = new Chat(
                        resultSet.getLong("chat_id"),
                        user,
                        user2,
                        resultSet.getTimestamp("chat_created_date"),
                        resultSet.getString("chat_latest_message_text"),
                        resultSet.getTimestamp("chat_latest_message_time")
                );
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chat;
    }

    public static List<Message> getAllChatMessages(Chat chat, User user, User user2) {
        List<Message> messages = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM messages " +
                    "INNER JOIN chats on messages.message_chat_id = chats.chat_id " +
                    "WHERE messages.message_chat_id = ? " +
                    "ORDER BY messages.message_sent_date;");
            preparedStatement.setLong(1, chat.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                messages.add(new Message(
                        resultSet.getLong("message_id"),
                        chat,
                        resultSet.getLong("message_receiver_id") == user.getId() ? user : user2,
                        resultSet.getLong("message_sender_id") == user.getId() ? user : user2,
                        resultSet.getString("message_text"),
                        resultSet.getBoolean("message_read_by_receiver"),
                        resultSet.getTimestamp("message_sent_date")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public static boolean addMessage(Message message) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO messages (message_chat_id, message_receiver_id, message_sender_id, message_text, message_read_by_receiver, message_sent_date) " +
                    "VALUES (?, ?, ?, ?, ?, now());");
            preparedStatement.setLong(1, message.getChat().getId());
            preparedStatement.setLong(2, message.getReceiver().getId());
            preparedStatement.setLong(3, message.getSender().getId());
            preparedStatement.setString(4, message.getText());
            preparedStatement.setBoolean(5, message.isReadByReceiver());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean markMessagesAsRead(User receiver, Chat chat) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE messages SET message_read_by_receiver=TRUE " +
                    "WHERE message_receiver_id = ? AND message_chat_id = ?;");
            preparedStatement.setLong(1, receiver.getId());
            preparedStatement.setLong(2, chat.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static List<Chat> getAllUserChats(User user) {
        List<Chat> chats = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM chats " +
                    "WHERE chat_user_id = ? OR chat_opponent_user_id = ? " +
                    "ORDER BY chat_latest_message_time DESC;");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                chats.add(new Chat(
                        resultSet.getLong("chat_id"),
                        getUserById(resultSet.getLong("chat_user_id")),
                        getUserById(resultSet.getLong("chat_opponent_user_id")),
                        resultSet.getTimestamp("chat_created_date"),
                        resultSet.getString("chat_latest_message_text"),
                        resultSet.getTimestamp("chat_latest_message_time")
                ));
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chats;
    }

    public static boolean updateChatsLatestMessage(Chat chat) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE chats SET chat_latest_message_text = ?, chat_latest_message_time = now() " +
                    "WHERE chat_id = ?;");
            preparedStatement.setString(1, chat.getLatestMessageText());
            preparedStatement.setLong(2, chat.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean deleteFriends(User user, User user2) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM friends " +
                    "WHERE friend_main_id = ? AND friend_secondary_id = ?;");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user2.getId());

            rows += preparedStatement.executeUpdate();

            preparedStatement.setLong(1, user2.getId());
            preparedStatement.setLong(2, user.getId());

            rows += preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    private static boolean createChat(User user, User user2) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO chats (chat_user_id, chat_opponent_user_id, chat_created_date) " +
                    "VALUES (?, ?, now());");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user2.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    private static boolean friendRequestAlreadyExists(User userSender, User receiverUser) {
        boolean res = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM friends_requests WHERE friend_request_sender_id = ? AND friend_request_receiver_id = ?;");
            preparedStatement.setLong(1, userSender.getId());
            preparedStatement.setLong(2, receiverUser.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            res = resultSet.next();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static boolean deleteFriendRequest(User senderUser, User receiverUser) {
        int rows = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM friends_requests WHERE friend_request_receiver_id = ? AND friend_request_sender_id = ?;");
            preparedStatement.setLong(1, receiverUser.getId());
            preparedStatement.setLong(2, senderUser.getId());

            rows = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }
}
