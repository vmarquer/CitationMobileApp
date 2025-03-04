import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.citationeapp.ui.theme.components.TextH2Bold
import com.example.citationeapp.ui.theme.iconLargeSize
import com.example.citationeapp.ui.theme.iconMediumSize
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