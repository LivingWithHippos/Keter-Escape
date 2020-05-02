package com.onewisebit.scpescape.model.entities

import androidx.room.*

@Entity(
    tableName = "votes",
    indices = [Index(value = arrayOf("turn", "round", "game", "player"))],
    foreignKeys = [ForeignKey(
        entity = Turn::class,
        parentColumns = ["turn_number", "round", "game", "player"],
        childColumns = ["turn", "round", "game", "player"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Player::class,
        parentColumns = ["player_ID"],
        childColumns = ["player_voted"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Vote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vote_ID")
    val id: Long,
    @ColumnInfo(name = "game")
    val gameID: Long,
    //todo: from turn number we get round and player, we can remove these here
    @ColumnInfo(name = "round")
    val roundNumber: Int,
    @ColumnInfo(name = "turn")
    val turnNumber: Int,
    @ColumnInfo(name = "player", index = true)
    val playerID: Long,
    @ColumnInfo(name = "player_voted", index = true)
    val votedPlayerID: Long,
    @ColumnInfo(name = "vote_action")
    val voteAction: String
) {
    override fun toString(): String {
        return "Vote(id=$id, gameID=$gameID, roundNumber=$roundNumber, turnNumber=$turnNumber, playerID=$playerID, votedPlayerID=$votedPlayerID, voteAction='$voteAction')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vote

        if (id != other.id) return false
        if (gameID != other.gameID) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + gameID.hashCode()
        return result
    }


}