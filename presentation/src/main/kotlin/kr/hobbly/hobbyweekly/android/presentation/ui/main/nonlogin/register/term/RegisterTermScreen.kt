package kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.term

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.sentry.Sentry
import io.sentry.SentryLevel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.MutableEventFlow
import kr.hobbly.hobbyweekly.android.common.util.coroutine.event.eventObserve
import kr.hobbly.hobbyweekly.android.domain.model.nonfeature.user.Term
import kr.hobbly.hobbyweekly.android.presentation.R
import kr.hobbly.hobbyweekly.android.presentation.common.theme.HeadlineRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral400
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Neutral900
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Red
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space10
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space24
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space32
import kr.hobbly.hobbyweekly.android.presentation.common.theme.Space80
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleMedium
import kr.hobbly.hobbyweekly.android.presentation.common.theme.TitleRegular
import kr.hobbly.hobbyweekly.android.presentation.common.theme.White
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.LaunchedEffectWithLifecycle
import kr.hobbly.hobbyweekly.android.presentation.common.util.compose.safeNavigate
import kr.hobbly.hobbyweekly.android.presentation.common.view.RippleBox
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButton
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonProperties
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonSize
import kr.hobbly.hobbyweekly.android.presentation.common.view.confirm.ConfirmButtonType
import kr.hobbly.hobbyweekly.android.presentation.ui.main.nonlogin.register.profile.RegisterProfileConstant
import timber.log.Timber

@Composable
fun RegisterTermScreen(
    navController: NavController,
    argument: RegisterTermArgument,
    data: RegisterTermData
) {
    val (state, event, intent, logEvent, handler) = argument
    val scope = rememberCoroutineScope() + handler

    val checkedTermList = remember { mutableStateListOf<String>() }

    val isNecessaryTermChecked = data.termList.all { term ->
        !term.isNecessary || checkedTermList.contains(term.id)
    }
    val isConfirmButtonEnabled = state != RegisterTermState.Loading && isNecessaryTermChecked

    fun navigateToRegisterProfile() {
        navController.safeNavigate(RegisterProfileConstant.ROUTE) {
            popUpTo(RegisterTermConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "서비스 약관동의",
            style = HeadlineRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space10))
        Text(
            text = "서비스 이용 동의가 필요해요",
            style = TitleRegular.merge(Neutral900)
        )
        Spacer(modifier = Modifier.height(Space80))
        data.termList.forEach { term ->
            RegisterTermScreenItem(
                term = term,
                isChecked = checkedTermList.contains(term.id),
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        checkedTermList.add(term.id)
                    } else {
                        checkedTermList.remove(term.id)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(Space80))
        ConfirmButton(
            modifier = Modifier
                .padding(start = 22.5.dp, end = 22.5.dp, bottom = 55.dp)
                .fillMaxWidth(),
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            isEnabled = isConfirmButtonEnabled,
            onClick = {
                intent(RegisterTermIntent.OnConfirm(checkedTermList))
            }
        ) { style ->
            Text(
                text = "다음",
                style = style
            )
        }
    }

    fun patchTerm(event: RegisterTermEvent.PatchTerm) {
        when (event) {
            RegisterTermEvent.PatchTerm.Success -> {
                navigateToRegisterProfile()
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegisterTermEvent.PatchTerm -> {
                    patchTerm(event)
                }
            }
        }
    }
}

@Composable
fun RegisterTermScreenItem(
    term: Term,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val text = if (term.isNecessary) {
        "[필수] ${term.title}"
    } else {
        "[선택] ${term.title}"
    }

    fun navigateToTermLink() {
        runCatching {
            val link = Uri.parse(term.link)
            val browserIntent = Intent(Intent.ACTION_VIEW, link)
            ContextCompat.startActivity(context, browserIntent, null)
        }.onFailure { exception ->
            Timber.d(exception)
            Sentry.withScope {
                it.level = SentryLevel.INFO
                Sentry.captureException(exception)
            }
            Firebase.crashlytics.recordException(exception)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Space32, vertical = Space10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (term.link.isEmpty()) {
            Text(
                text = text,
                style = TitleMedium.merge(
                    color = Neutral900
                )
            )
        } else {
            Text(
                modifier = Modifier.clickable {
                    navigateToTermLink()
                },
                text = text,
                style = TitleMedium.merge(
                    color = Neutral900,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        RippleBox(
            onClick = {
                onCheckedChange(!isChecked)
            }
        ) {
            Icon(
                modifier = Modifier.size(Space24),
                painter = painterResource(id = R.drawable.ic_check_box_checked),
                contentDescription = null,
                tint = if (isChecked) Red else Neutral400
            )
        }
    }
}

@Preview
@Composable
private fun RegisterTermScreenPreview() {
    RegisterTermScreen(
        navController = rememberNavController(),
        argument = RegisterTermArgument(
            state = RegisterTermState.Init,
            event = MutableEventFlow(),
            intent = {},
            logEvent = { _, _ -> },
            handler = CoroutineExceptionHandler { _, _ -> }
        ),
        data = RegisterTermData(
            termList = listOf(
                Term(
                    id = "AA001",
                    title = "개인정보 수집 이용동의",
                    link = "https://www.naver.com",
                    isNecessary = true
                ),
                Term(
                    id = "AA002",
                    title = "고유식별 정보처리 동의",
                    link = "https://www.naver.com",
                    isNecessary = true
                ),
                Term(
                    id = "AA003",
                    title = "통신사 이용약관 동의",
                    link = "https://www.naver.com",
                    isNecessary = true
                ),
                Term(
                    id = "AA004",
                    title = "14세 이상 동의",
                    link = "",
                    isNecessary = false
                )
            )
        )
    )
}
