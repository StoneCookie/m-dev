import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.hw_6.R
import com.example.hw_6.database.Node
import com.example.hw_6.databinding.AddFragmentBinding
import com.example.hw_6.viewmodels.NodeViewModel

class AddFragment : DialogFragment() {

    private lateinit var binding: AddFragmentBinding
    private val model: NodeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddFragmentBinding.inflate(LayoutInflater.from(context))

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.setPositiveButton(R.string.save) {d, id ->
                val txt = binding.text.text.toString()
                if (!TextUtils.isEmpty(txt)) {addTxt(txt)}
            }
            builder.setNegativeButton(R.string.cancel) {d, id -> d.cancel()}
            builder.create()
        } ?: throw IllegalStateException("Invalid value (null)")
    }

    private fun addTxt(txt: String) {
        val node = Node(0, txt, mutableListOf<Node>())

        model.insert(node)
    }
}
