import android.text.style.LineBackgroundSpan
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.citationeapp.ui.theme.iconButtonSize
import com.example.citationeapp.ui.theme.iconToggleSize
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary
import com.example.citationeapp.ui.theme.white

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
            modifier = Modifier.size(iconToggleSize)
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
        shape = RoundedCornerShape(padding12),
        colors = ButtonDefaults.buttonColors(containerColor = primary),
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = white,
            modifier = Modifier.size(iconButtonSize)
        )
        Text(
            text = (if (textId == -1) text else stringResource(id = textId)),
            color = white,
            modifier = Modifier.padding(start = padding8)
        )
    }
}