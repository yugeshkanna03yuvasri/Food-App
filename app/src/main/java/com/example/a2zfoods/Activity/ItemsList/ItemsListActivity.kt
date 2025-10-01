package com.example.a2zfoods.Activity.ItemsList

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.a2zfoods.R
import com.example.a2zfoods.ViewModel.MainViewModel
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



class ItemsListActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var id: String = ""
    private var title: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        id= intent.getStringExtra("id")?:""
        title= intent.getStringExtra("title")?:""

        setContent {
            ItemListScreen(
                title = title,
                onBackClick = {finish()},
                viewModel= viewModel,
                id = id
            )
        }
    }
}

@Composable
private fun ItemListScreen(
    title: String,
    onBackClick: () -> Unit,
    viewModel: MainViewModel,
    id : String
){
    val items by viewModel.loadFiltered(id).observeAsState(emptyList())
    var isloading by remember { mutableStateOf(true) }
    LaunchedEffect(id){
        viewModel.loadFiltered(id)
    }
    LaunchedEffect(items){
        isloading = items.isEmpty()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        ConstraintLayout(modifier = Modifier.padding(top = 36.dp , start = 16.dp , end = 16.dp)) {
            val (backBtn , cartTxt) = createRefs()
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartTxt){centerTo(parent)},
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                text = title
            )
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.clickable{ onBackClick() }
                    .constrainAs(backBtn){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }

            )
        }
        if (isloading){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }
        }else{
            ItemsList(items)
        }
    }
}