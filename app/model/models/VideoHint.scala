package model.models

import types.TokenId

object VideoHint {}
/*
 * VideoHint
 * Keeps a list of all the videos a user has watched to be iterated over once per hour in order
 * to give the user their stars back.
 * */
case class VideoHint(tokenId: TokenId, videosWatched: List[HintsTaken])
