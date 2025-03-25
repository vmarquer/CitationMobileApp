import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.components.TextBody1Bold
import com.example.citationeapp.ui.theme.components.TextBody1Regular
import com.example.citationeapp.ui.theme.heightAnswerButton
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding2
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.spacing16
import com.example.citationeapp.ui.theme.white

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    @StringRes textId: Int = -1
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(containerColor = primary),
    ) {
        TextBody1Bold(
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
    borderColor: Color,
    textColor: Color,
) {
    var isPressed by remember { mutableStateOf(false) }
    Button(
        onClick = {
            isPressed = true
            onClick()
                  },
        modifier = modifier.fillMaxWidth().heightIn(min = heightAnswerButton)
            .background(backgroundColor, shape = RoundedCornerShape(padding8))
            .border(lineHeightSmall, borderColor, shape = RoundedCornerShape(padding8))
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
            TextBody1Regular(
                text = if (textId == -1) text else stringResource(id = textId),
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    iconId: ImageVector,
    colorIcon: Color,
    @StringRes textId: Int = -1,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(padding8),
        colors = ButtonDefaults.buttonColors(containerColor = primary.copy(0.2f)),
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = padding2),
            horizontalArrangement = Arrangement.spacedBy(
                spacing16, alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconId,
                contentDescription = null,
                tint = colorIcon,
            )
            TextBody1Bold(
                text = (if (textId == -1) text else stringResource(id = textId)),
                color = black,
                modifier = Modifier
            )
        }
    }
}