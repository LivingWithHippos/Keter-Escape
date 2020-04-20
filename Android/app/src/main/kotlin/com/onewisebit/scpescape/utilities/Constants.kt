package com.onewisebit.scpescape.utilities

import com.onewisebit.scpescape.R

/**
 * Constants used throughout the app.
 */

// db files

const val DATABASE_NAME = "SCP.db"

// assets files and folders

const val TEMPLATE_FOLDER = "templates/"
const val TEMPLATE_FILE_INFO = "info.json"
const val TEMPLATE_FILE_VOTE = "vote.json"

const val MODE_FILE = "mode.json"
const val ACTION_FOLDER = "actions/"
//todo: use only these or template const
//todo: we can get the names from the template, easier to use in the long term
const val VOTE_FILE = "vote.json"
const val INFO_FILE = "info.json"
const val ROLE_FOLDER = "roles/"
const val ROLE_FILE = "roles.json"
const val ROUND_FOLDER = "rounds/"
const val ROUND_FILE = "rounds.json"
const val VICTORY_FOLDER = "victory/"
const val VICTORY_FILE = "victory.json"

// shared preferences

const val PREF_FILE = "scpescape_preferences"
const val DEFAULT_THEME = "theme_foundation"
const val CURRENT_THEME = "preference_current_theme"

// game values

const val GAME_TYPE = "game_type"
const val GAME_TYPE_PASS = 0
const val GAME_TYPE_LOCAL = 1
const val GAME_TYPE_NET = 2

// game states

const val PARTICIPANT_STATE_DEAD = 0
const val PARTICIPANT_STATE_ALIVE = 1

// Classic mode values

const val DAY = 0
const val NIGHT = 1

// Victory conditions type

const val DEAD_GROUP = "dead_group"
const val WLE_GROUP = "win_if_less_or_equal"
const val WL_GROUP = "win_if_less"
const val WGE_GROUP = "win_if_greater_or_equal"
const val WG_GROUP = "win_if_greater"

// role powers

const val POWER_VOTE = "vote"
const val POWER_INFO = "info"

// Bundle arguments

const val ARG_TURN_NUMBER = "turn_number"
const val ARG_PLAYER_NAME = "player_name"
const val ARG_ROLE_NAME = "role_name"
const val ARG_ROUND_CODE = "round_code"
const val ARG_ROLE_DESCRIPTION = "role_description"
const val ARG_ACTION_INFO_TITLE = "info_title"
const val ARG_ACTION_INFO_TITLE_DESCRIPTION = "info_description"
const val ARG_LAST_TURN = "last_turn"
const val ARG_KILLED_PLAYERS = "killed_players"

// list item types

const val TYPE_VOTE: Int = R.layout.vote_list_item