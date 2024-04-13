package kr.hobbly.hobbyweekly.android.data.remote.network.environment

import android.content.Context
import android.content.pm.ApplicationInfo
import kr.hobbly.hobbyweekly.android.data.R
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.error.UndefinedKeyException
import timber.log.Timber

class ErrorMessageMapper(
    private val context: Context
) {
    fun map(
        id: Long
    ): String {
        val isDebug: Boolean =
            (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        val resourceId = when (id) {
            KEY_INTERNAL_SERVER_ERROR -> R.string.error_internal_server_error
            URL_NOT_FOUND -> R.string.error_url_not_found
            INVALID_PARAMETER -> R.string.error_invalid_parameter
            INVALID_JWT_TOKEN -> R.string.error_invalid_jwt_token
            EXPIRED_JWT_TOKEN -> R.string.error_expired_jwt_token
            UNSUPPORTED_JWT_TOKEN -> R.string.error_unsupported_jwt_token
            REFRESH_TOKEN_INVALID -> R.string.error_refresh_token_invalid
            EMPTY_JWT_TOKEN -> R.string.error_empty_jwt_token
            INVALID_REFRESH_TOKEN -> R.string.error_invalid_refresh_token
            ADMIN_UNAUTHORIZED -> R.string.error_admin_unauthorized
            WRITER_UNAUTHORIZED -> R.string.error_writer_unauthorized
            MAKER_UNAUTHORIZED -> R.string.error_maker_unauthorized
            BLOCK_MEMBER_UNAUTHORIZED -> R.string.error_block_member_unauthorized
            MEMBER_NOT_FOUND -> R.string.error_member_not_found
            REFRESH_TOKEN_NOT_FOUND -> R.string.error_refresh_token_not_found
            BOARD_NOT_FOUND -> R.string.error_board_not_found
            BLOCK_NOT_FOUND -> R.string.error_block_not_found
            POST_NOT_FOUND -> R.string.error_post_not_found
            TERM_NOT_FOUND -> R.string.error_term_not_found
            COMMENT_NOT_FOUND -> R.string.error_comment_not_found
            NOTIFICATION_NOT_FOUND -> R.string.error_notification_not_found
            BIG_ROUTINE_NOT_FOUND -> R.string.error_big_routine_not_found
            ALREADY_JOINED_BLOCK -> R.string.error_already_joined_block
            ALREADY_LIKED_COMMENT -> R.string.error_already_liked_comment
            ALREADY_LIKED_POST -> R.string.error_already_liked_post
            NOT_ALLOWED_ROUTINE_DAY -> R.string.error_not_allowed_routine_day
            NOT_ALLOWED_SELF_REPORT -> R.string.error_not_allowed_self_report
            else -> {
                Timber.e(UndefinedKeyException("Undefined error key: $id"))
                R.string.error_unknown
            }
        }

        return if (isDebug) {
            context.getString(resourceId) + "\n($id)"
        } else {
            context.getString(resourceId)
        }
    }

    companion object {
        const val KEY_INTERNAL_SERVER_ERROR: Long = 500001L // Internal server error
        const val URL_NOT_FOUND: Long = 400001L // Not found resource url
        const val INVALID_PARAMETER: Long = 400002L // Invalid parameter
        const val INVALID_JWT_TOKEN: Long = 401001L // Invalid jwt token
        const val EXPIRED_JWT_TOKEN: Long = 401002L // Expired jwt token
        const val UNSUPPORTED_JWT_TOKEN: Long = 401003L // Unsupported jwt token
        const val REFRESH_TOKEN_INVALID: Long = 401004L // Invalid Refresh Token
        const val EMPTY_JWT_TOKEN: Long = 401005L // Empty jwt token
        const val INVALID_REFRESH_TOKEN: Long = 401006L // Valid jwt token, but different with DB
        const val ADMIN_UNAUTHORIZED: Long = 401007L // Admin only
        const val WRITER_UNAUTHORIZED: Long = 403001L // Writer only
        const val MAKER_UNAUTHORIZED: Long = 403002L // Maker only
        const val BLOCK_MEMBER_UNAUTHORIZED: Long = 403003L // Block member only
        const val MEMBER_NOT_FOUND: Long = 404101L // Not exist member
        const val REFRESH_TOKEN_NOT_FOUND: Long = 404102L // Not found refresh token in DB
        const val BOARD_NOT_FOUND: Long = 404103L // Not found board
        const val BLOCK_NOT_FOUND: Long = 404104L // Not found block
        const val POST_NOT_FOUND: Long = 404106L // Not found post
        const val TERM_NOT_FOUND: Long = 404109L // Not found term
        const val COMMENT_NOT_FOUND: Long = 404110L // Not found comment
        const val NOTIFICATION_NOT_FOUND: Long = 404111L // Not found notification
        const val BIG_ROUTINE_NOT_FOUND: Long = 404112L // Not found big routine
        const val ALREADY_JOINED_BLOCK: Long = 409102L // Member already join block
        const val ALREADY_LIKED_COMMENT: Long = 409103L // Already like comment
        const val ALREADY_LIKED_POST: Long = 409104L // Already like post
        const val NOT_ALLOWED_ROUTINE_DAY: Long = 409105L // Not allowed routine day
        const val NOT_ALLOWED_SELF_REPORT: Long = 409106L // Not allowed self report
    }
}
