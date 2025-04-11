package com.coppel.technicaltest.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coppel.technicaltest.domain.model.FactModel
import com.coppel.technicaltest.ui.screens.home.HomeViewModel

@Composable
fun HomeSuccess(
    homeViewModel: HomeViewModel
) {
    val homeState = homeViewModel.homeState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFC7C4EE))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = homeState.searchQuery,
            onValueChange = { homeViewModel.onSearchQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = {
                Text(text = "Search by Organization")
                    },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "User",
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(FakeFacts.list) { item ->
                ListItem(
                    organization = item.organization,
                    source = item.resource,
                    fact = item.fact
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeSuccessPreview() {
    HomeSuccess(
        homeViewModel = HomeViewModel()
    )
}

object FakeFacts {
    val list = listOf(
        FactModel(
            uid = "5818ede7bb681ad20c18e65a",
            dateInsert = "2016-11-01T19:32:55.368Z",
            slug = "sct",
            columns = "L,N,O",
            fact = "Hay 5,510 sitios públicos con conectividad wifi en el país.",
            organization = "SCT",
            resource = "Tabla con sitios con conectividad a internet (México Conectado)",
            url = "http://datos.gob.mx/busca/dataset/infraestructura-de-telecomunicaciones",
            operations = "tabla",
            dataset = "Infraestructura de Telecomunicaciones",
            createdAt = 1461620047
        ),
        FactModel(
            uid = "5818ede7bb681ad20c18e65b",
            dateInsert = "2017-05-10T12:15:23.000Z",
            slug = "inegi",
            columns = "A,B,C",
            fact = "En México existen más de 126 millones de habitantes.",
            organization = "INEGI",
            resource = "Población total por entidad federativa",
            url = "https://datos.gob.mx/busca/dataset/poblacion-total",
            operations = "tabla",
            dataset = "Censo de Población",
            createdAt = 1494412523
        ),
        FactModel(
            uid = "5818ede7bb681ad20c18e65c",
            dateInsert = "2018-09-15T08:45:00.000Z",
            slug = "sep",
            columns = "X,Y,Z",
            fact = "Más de 25 millones de estudiantes están inscritos en educación básica.",
            organization = "SEP",
            resource = "Matrícula escolar por nivel",
            url = "https://datos.gob.mx/busca/dataset/matricula-educacion-basica",
            operations = "tabla",
            dataset = "Educación Básica",
            createdAt = 1537001100
        ),
        FactModel(
            uid = "5818ede7bb681ad20c18e65d",
            dateInsert = "2019-01-21T16:22:11.000Z",
            slug = "salud",
            columns = "D,E,F",
            fact = "El 85% de la población mexicana tiene acceso a servicios de salud.",
            organization = "Secretaría de Salud",
            resource = "Cobertura de salud en México",
            url = "https://datos.gob.mx/busca/dataset/salud-cobertura",
            operations = "tabla",
            dataset = "Salud Pública",
            createdAt = 1548080531
        ),
        FactModel(
            uid = "5818ede7bb681ad20c18e65e",
            dateInsert = "2020-03-30T10:12:00.000Z",
            slug = "conagua",
            columns = "G,H,I",
            fact = "El 95% del agua potable en México proviene de fuentes subterráneas.",
            organization = "CONAGUA",
            resource = "Fuentes de abastecimiento de agua",
            url = "https://datos.gob.mx/busca/dataset/agua-potable",
            operations = "tabla",
            dataset = "Agua y Saneamiento",
            createdAt = 1585565520
        )
    )
}