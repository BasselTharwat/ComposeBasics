package com.example.basics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basics.ui.theme.BasicsTheme
import com.example.basics.ui.theme.Typography
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicsTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        if(shouldShowOnboarding){
            OnBoardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        }else{
            Greetings(modifier = Modifier.padding(top = 30.dp))
        }
    }
}


@Composable
private fun Greetings(modifier: Modifier = Modifier,
                      names : List<String> = List(1000) { "$it"+1 } ){
    LazyColumn (modifier = modifier){
        items(items = names){ name ->
            Greeting(name = name)

        }
    }
}


@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier,
                     onContinueClicked: ()-> Unit){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Welcome onboard!")
        Button(modifier = Modifier.padding(24.dp),
            onClick = onContinueClicked) {
            Text(text = "Continue")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    /*val extraPadding by animateDpAsState(
        targetValue = if(expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
     */
    Surface(color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row (modifier = Modifier
            .padding(24.dp)
            //.padding(bottom = extraPadding.coerceAtLeast(0.dp))
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )) {
            Column (modifier = Modifier
                .weight(1f)) {
                Text(text = "Hello,")
                Text(text = "$name ",
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            fontWeight = FontWeight.ExtraBold
                        ))
                if(expanded){
                    Text(text = ("lorem ipsum \n lorem ipsum").repeat(4))

                }
            }
            IconButton(onClick = { expanded = !expanded },
                modifier = Modifier.padding(bottom = 32.dp)) {
                if(!expanded){
                    Icon(imageVector = Icons.Default.ExpandMore ,
                        contentDescription = "Show more")
                }else{
                    Icon(imageVector = Icons.Default.ExpandLess,
                        contentDescription = "Show less")
                }
                Icon(imageVector = if(expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if(expanded) "Show less" else "Show more")
            }
        }



    }

}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun MyAppPreview(){
    BasicsTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun GreetingPreview() {
    BasicsTheme {
        Greetings(modifier = Modifier.padding(top = 30.dp))
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardPreview(){
    BasicsTheme {
        OnBoardingScreen(onContinueClicked = {}) //do nothing on click
    }
}

