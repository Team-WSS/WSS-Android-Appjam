package com.teamwss.websoso.ui.main.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teamwss.websoso.data.Memo
import com.teamwss.websoso.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {
    private lateinit var binding: FragmentRecordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val memoAdapter = RecordAdapter(requireContext())
        binding.rvRecord.adapter = memoAdapter
        memoAdapter.setFriendList(mockMemoList)
    }

    private val mockMemoList = listOf<Memo>(
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

    companion object {
        fun newInstance() = RecordFragment()
    }
}