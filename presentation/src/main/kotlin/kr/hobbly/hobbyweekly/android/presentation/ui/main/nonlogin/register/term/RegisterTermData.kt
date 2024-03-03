package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

import androidx.compose.runtime.Immutable
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term

@Immutable
data class RegisterTermData(
    val termList: List<Term>
)
