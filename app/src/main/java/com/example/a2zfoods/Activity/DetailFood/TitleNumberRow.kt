package com.example.a2zfoods.Activity.DetailFood

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.a2zfoods.Domain.FoodModel
import com.example.a2zfoods.Helper.previewFood
import com.example.a2zfoods.R

/*The TitleNumberRow composable displays a food item’s title along with a
quantity selector that lets users increment or decrement the number in the cart.*/

@Composable
@Preview(showBackground = false)
fun TitleNumberRow(
    item: FoodModel = previewFood,
    numberInCart:Int= 1,
    onIncrement:() -> Unit={},
    onDecrement:() -> Unit={},
    modifier: Modifier= Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(text = item.Title ,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = colorResource(R.color.black),
            modifier= Modifier.weight(1f)
        )
        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 8.dp)
                .shadow(
                    elevation = 8.dp,  // shadow size
                    shape = CircleShape,  // keep it circular
                    clip = false
                )
                .background(shape = RoundedCornerShape(100.dp),
                    color = colorResource(R.color.white))

        ) {
            val(plusCartBtn , minusCartBtn, numberItemText) = createRefs()
            Text(text = "$numberInCart",
                color = colorResource(R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .size(28.dp)
                    .background(color = Color.White , shape = CircleShape)
                    .wrapContentSize(Alignment.Center)
                    .constrainAs(numberItemText){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }
            )
            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .background(color = colorResource(R.color.green),
                    RoundedCornerShape(100.dp)
                    )
                .constrainAs(plusCartBtn){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable{onIncrement()}
            ){
                Text(
                    text = "+",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )

            }

            Box(modifier = Modifier
                .padding(2.dp)
                .size(28.dp)
                .background(color = colorResource(R.color.grey),
                    RoundedCornerShape(100.dp)
                )
                .constrainAs(minusCartBtn){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable{onDecrement() }
            ){
                Text(
                    text = "-",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}