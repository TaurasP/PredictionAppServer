package eif.viko.lt.predictionappserver.Utils;

public class EmailToNameConverter {

    public static String getNameFromEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Invalid email provided.");
        }

        // Split the email at the '@' symbol
        String[] parts = email.split("@");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Take the part before '@' and split it into name and surname
        String[] nameParts = parts[0].split("\\.");
        if (nameParts.length != 2) {
            throw new IllegalArgumentException("Email does not contain a proper name format.");
        }

        // Capitalize the first letter of each part and concatenate them
        String name = capitalizeFirstLetter(nameParts[0]);
        String surname = capitalizeFirstLetter(nameParts[1]);

        return surname + " " + name;
    }

    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
