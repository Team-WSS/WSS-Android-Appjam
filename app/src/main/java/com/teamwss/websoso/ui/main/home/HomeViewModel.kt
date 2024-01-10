package com.teamwss.websoso.ui.main.home

import androidx.lifecycle.ViewModel
import com.teamwss.websoso.data.model.HomeNovelEntity

class HomeViewModel : ViewModel() {
    val mockSosoPickData = listOf<HomeNovelEntity>(
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 100,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목 적당히 긴거",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 50,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목 엄엄엄엄엄청~~~~~~~~!~~~~~~~~~~긴거",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 30,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 100,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 100,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 100,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
        HomeNovelEntity(
            novelId = 1,
            novelTitle = "소설 제목",
            novelAuthor = "작가 이름",
            novelRegisteredCount = 100,
            novelImg = "https://github.com/Team-WSS/WSS-Android/assets/52442547/e2fd89cf-b81f-4e2c-8b75-aa4f5dd78b86"
        ),
    )
}