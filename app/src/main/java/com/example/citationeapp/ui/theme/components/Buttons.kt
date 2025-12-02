import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.example.citationeapp.ui.theme.components.TextBody3Regular
import com.example.citationeapp.ui.theme.heightAnswerButton
import com.example.citationeapp.ui.theme.lineHeightSmall
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.white

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
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
fun ButtonSecondary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "",
    @StringRes textId: Int = -1
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(padding8),
        border = BorderStroke(lineHeightSmall, primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = white,
            contentColor = primary
        ),
    ) {
        TextBody1Bold(
            text = if (textId == -1) text else stringResource(id = textId),
            color = primary,
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
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = heightAnswerButton)
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
fun FloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: ImageVector,
    contentDescription: String? = null,
    containerColor: Color = primary,
    iconTint: Color = white
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = FloatingActionButtonDefaults.shape,
        containerColor = containerColor,
        contentColor = iconTint
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SingleChoiceSegmentedButton(
    options: List<T>,
    modifier: Modifier = Modifier,
    selectedOption: T,
    getText: @Composable (T) -> String,
    onSelectionChanged: (T) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                selected = option == selectedOption,
                onClick = { onSelectionChanged(option) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = primary,
                    inactiveContainerColor = white,
                    activeContentColor = white
                ),
                label = {
                    TextBody3Regular(
                        text = getText(option),
                        color = if (option == selectedOption) white else black
                    )
                }
            )
        }
    }
}
