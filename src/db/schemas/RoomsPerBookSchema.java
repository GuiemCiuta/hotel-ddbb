package db.schemas;

// A table to relate rooms and books (a single book could contain more than a room)
// We don't add room id here because the room is given on check-in
public enum RoomsPerBookSchema {
    BOOK_ID, ROOM_TYPE, PEOPLE_NUM, BREAKFAST, LUNCH, DINNER, CANCELED;
}
