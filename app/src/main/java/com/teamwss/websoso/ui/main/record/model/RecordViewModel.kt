package com.teamwss.websoso.ui.main.record.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {
    private val _novelCount = MutableLiveData<String>()
    val novelCount: LiveData<String> get() = _novelCount

    private val _memoData = MutableLiveData<List<Memo>>()
    val memoData: LiveData<List<Memo>> get() = _memoData

    init {
        _novelCount.value = "0"
        _memoData.value = getMockMemoList()
    }

    private fun getMockMemoList(): List<Memo> {
        return listOf(
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
            Memo(
                novelDate = "2023-12-23 오전 10:12",
                novelTitle = "당신의 이해를 돕기 위해서",
                novelContent = "깨달았다. 사람은 사람을 절대 이해할 수 없다. 공감할수는 있어도. 그렇기에 나는 절대로 사람을 이해하려 노력하지 않을 것이다. 왜냐하면 어차피 이해하지 못할 것이기 때문이다. 너무 속상하다. 왜 사람은 절대 사람을 이해하지 못한다고 한걸까?"
            ),
        )
    }


}