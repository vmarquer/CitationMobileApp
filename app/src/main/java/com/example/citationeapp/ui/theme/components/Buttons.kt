import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextH2Bold
import com.example.citationeapp.ui.theme.components.TextH3
import com.example.citationeapp.ui.theme.components.TextH3Bold
import com.example.citationeapp.ui.theme.dimZero
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.heightAnswerButton
import com.example.citationeapp.ui.theme.iconLargeSize
import com.example.citationeapp.ui.theme.iconMediumSize
import com.example.citationeapp.ui.theme.lineHeightMedium
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.white

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    text: String = "",
    @StringRes textId: Int = -1,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(containerColor = primary),
    ) {
        TextH2Bold(
            text = (if (textId == -1) text else stringResource(id = textId)),
            color = white,
            modifier = Modifier
        )
    }
}

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    @StringRes textId: Int = -1,
    enabled: Boolean,
    backgroundColor: Color,
) {
    var isPressed by remember { mutableStateOf(false) }
    Button(
        onClick = {
            isPressed = true
            onClick()
                  },
        modifier = modifier.fillMaxWidth().heightIn(min = heightAnswerButton)
            .background(backgroundColor, shape = RoundedCornerShape(padding8))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) Color(0xFF003366) else backgroundColor,
        ),
        enabled = enabled,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isPressed) {
                TextH2Bold(
                    text = if (textId == -1) text else stringResource(id = textId),
                    color = white,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                TextH3(
                    text = if (textId == -1) text else stringResource(id = textId),
                    color = white,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}

@Composable
fun RoundedIconButton(
    onClick: () -> Unit,
    iconId: Int,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .padding(padding8)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(iconMediumSize)
        )
    }
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    iconId: Int,
    color: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(padding8))
            .padding(padding8),
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(iconMediumSize)
        )
    }
}

@Composable
fun IconTextButton(
    onClick: () -> Unit,
    text: String = "",
    @StringRes textId: Int = -1,
    iconId: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(containerColor = primary),
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = white,
            modifier = Modifier.size(iconLargeSize)
        )
        TextH2Bold(
            text = (if (textId == -1) text else stringResource(id = textId)).uppercase(),
            color = white,
            modifier = Modifier.padding(start = padding8)
        )
    }
}

@Composable
fun TextIconButton(
    onClick: () -> Unit,
    text: String = "",
    @StringRes textId: Int = -1,
    iconId: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(containerColor = primary),
    ) {
        TextH2Bold(
            text = (if (textId == -1) text else stringResource(id = textId)).uppercase(),
            color = white,
            modifier = Modifier.padding(end = padding8)
        )
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = white,
            modifier = Modifier.size(iconLargeSize)
        )
    }
}