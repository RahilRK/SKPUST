package com.rahilkarim.skpust.ui.SetUpPageFrag

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rahilkarim.skpust.ui.BusinessDetailFrag.BusinessDetailModel
import com.rahilkarim.skpust.ui.FeedFrag.FeedModel
import com.rahilkarim.skpust.ui.MatrimonyFrag.MatrimonyUserDetailModel
import com.rahilkarim.skpust.ui.PeopleFrag.PeopleListModel
import com.rahilkarim.skpust.R
import com.rahilkarim.skpust.util.GlobalClass
import com.rahilkarim.skpust.util.Repository
import com.rahilkarim.skpust.util.skpustApplication
import com.rahilkarim.skpust.databinding.FragmentSetUpPageBinding
import com.rahilkarim.skpust.util.Constant.setUpPageTime
import kotlinx.coroutines.*

class SetUpPageFrag : Fragment() {

    var TAG = "SetUpPageFrag"
    private lateinit var activity: Context

    lateinit var globalClass: GlobalClass
    lateinit var binding: FragmentSetUpPageBinding
    lateinit var repository: Repository
    lateinit var viewModel: SetUpPageFragVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSetUpPageBinding.inflate(layoutInflater, container, false)

        init()
        addData()

        return binding.root
    }

    fun init() {
//        globalClass = GlobalClass.getInstance(activity)
        globalClass = (requireActivity().application as skpustApplication).globalClass
        repository = (requireActivity().application as skpustApplication).repository
        viewModel = ViewModelProvider(this, SetUpPageFragVMFactory(repository))
            .get(SetUpPageFragVM::class.java)
    }

    fun addData() {

        lifecycleScope.launch(Dispatchers.IO) {

            val addBusinessDetailJob = async {
                addBusinessDetail()
            }
            addBusinessDetailJob.await()

            val addPeopleDetailJob = async {
                addPeopleDetail()
            }
            addPeopleDetailJob.await()

            val addFeedDetailJob = async {
                addFeedDetail()
            }
            addFeedDetailJob.await()

            val addMatrimonyUserDetailJob = async {
                addMatrimonyUserDetail()
            }
            addMatrimonyUserDetailJob.await()

            nextScreen()
        }
    }

    //todo addBusiness
    suspend fun addBusinessDetail() {

        val arrayList = ArrayList<BusinessDetailModel>()
        arrayList.add(
            BusinessDetailModel(
                0,
                "Shubham Developers_1",
                "Builder",
                "Akshay Patel",
                "7864532321",
                "shubhamDev1999@gmail.com",
                "1999",
                "Shubham Developers is Gujarat’s one of the greatest emerging real estate developers with signature projects especially in the cities of Ahmedabad, Vapi & Umbergaon. Commenced in 1991 by the hands of a highly skilled technical planner with a vision to create themed landscaping, Shubham Developers has transformed itself from a small company started into a whole concept company that engineers ideas into reality.",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/business1_banner_image.jpg?alt=media&token=a2264546-fcfd-4cfe-a1a9-1a820a7ad94b",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/Aastha_Residency.jpg?alt=media&token=c5647e61-6133-4b62-81d8-95fdfba8ea81",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_industrial_hub.jpg?alt=media&token=ef70bd73-20b6-4c57-a9a1-e44a4dcc11c9",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_landmark.jpg?alt=media&token=a52151d5-3285-4e23-b7e6-78b4b975ebcd",
                "608/A, Pinnacle Business Park,\n" +
                        "Corporate Road, Near Prahladnagar Garden,\n" +
                        "Ahmedabad - 380015.",
                "Ahmedabad",
                "Gujarat",
                "380015",
                "0.0"
            )
        )

        arrayList.add(
            BusinessDetailModel(
                0,
                "Shubham Developers_2",
                "Builder",
                "Akshay Patel",
                "7864532321",
                "shubhamDev1999@gmail.com",
                "1999",
                "Shubham Developers is Gujarat’s one of the greatest emerging real estate developers with signature projects especially in the cities of Ahmedabad, Vapi & Umbergaon. Commenced in 1991 by the hands of a highly skilled technical planner with a vision to create themed landscaping, Shubham Developers has transformed itself from a small company started into a whole concept company that engineers ideas into reality.",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/business2_banner_image.jpg?alt=media&token=fef381b2-243a-4f27-b942-8dd59541a1fd",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_green_city_phase_2.jpg?alt=media&token=7312e3c2-773d-4ac1-8299-3903e72b6102",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_elite.jpg?alt=media&token=6aa63e6c-a748-4b07-ba74-518c46461ecf",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_industrial_hub.jpg?alt=media&token=ef70bd73-20b6-4c57-a9a1-e44a4dcc11c9",
                "608/A, Pinnacle Business Park,\n" +
                        "Corporate Road, Near Prahladnagar Garden,\n" +
                        "Ahmedabad - 380015.",
                "Ahmedabad",
                "Gujarat",
                "380015",
                "0.0"
            )
        )

        arrayList.add(
            BusinessDetailModel(
                0,
                "Shubham Developers_3",
                "Builder",
                "Akshay Patel",
                "7864532321",
                "shubhamDev1999@gmail.com",
                "1999",
                "Shubham Developers is Gujarat’s one of the greatest emerging real estate developers with signature projects especially in the cities of Ahmedabad, Vapi & Umbergaon. Commenced in 1991 by the hands of a highly skilled technical planner with a vision to create themed landscaping, Shubham Developers has transformed itself from a small company started into a whole concept company that engineers ideas into reality.",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/business3_banner_image.jpg?alt=media&token=4de10003-97c5-4fc6-83f2-706cc52ae2a7",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_elite.jpg?alt=media&token=6aa63e6c-a748-4b07-ba74-518c46461ecf",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_green_city_phase_2.jpg?alt=media&token=7312e3c2-773d-4ac1-8299-3903e72b6102",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_industrial_hub.jpg?alt=media&token=ef70bd73-20b6-4c57-a9a1-e44a4dcc11c9",
                "608/A, Pinnacle Business Park,\n" +
                        "Corporate Road, Near Prahladnagar Garden,\n" +
                        "Ahmedabad - 380015.",
                "Ahmedabad",
                "Gujarat",
                "380015",
                "0.0"
            )
        )

        arrayList.add(
            BusinessDetailModel(
                0,
                "Shubham Developers_4",
                "Builder",
                "Akshay Patel",
                "7864532321",
                "shubhamDev1999@gmail.com",
                "1999",
                "Shubham Developers is Gujarat’s one of the greatest emerging real estate developers with signature projects especially in the cities of Ahmedabad, Vapi & Umbergaon. Commenced in 1991 by the hands of a highly skilled technical planner with a vision to create themed landscaping, Shubham Developers has transformed itself from a small company started into a whole concept company that engineers ideas into reality.",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/business2_banner_image.jpg?alt=media&token=fef381b2-243a-4f27-b942-8dd59541a1fd",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_green_city_phase_2.jpg?alt=media&token=7312e3c2-773d-4ac1-8299-3903e72b6102",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/shubham_elite.jpg?alt=media&token=6aa63e6c-a748-4b07-ba74-518c46461ecf",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_industrial_hub.jpg?alt=media&token=ef70bd73-20b6-4c57-a9a1-e44a4dcc11c9",
                "608/A, Pinnacle Business Park,\n" +
                        "Corporate Road, Near Prahladnagar Garden,\n" +
                        "Ahmedabad - 380015.",
                "Ahmedabad",
                "Gujarat",
                "380015",
                "0.0"
            )
        )

        arrayList.add(
            BusinessDetailModel(
                0,
                "Shubham Developers_5",
                "Builder",
                "Akshay Patel",
                "7864532321",
                "shubhamDev1999@gmail.com",
                "1999",
                "Shubham Developers is Gujarat’s one of the greatest emerging real estate developers with signature projects especially in the cities of Ahmedabad, Vapi & Umbergaon. Commenced in 1991 by the hands of a highly skilled technical planner with a vision to create themed landscaping, Shubham Developers has transformed itself from a small company started into a whole concept company that engineers ideas into reality.",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/business1_banner_image.jpg?alt=media&token=a2264546-fcfd-4cfe-a1a9-1a820a7ad94b",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/Aastha_Residency.jpg?alt=media&token=c5647e61-6133-4b62-81d8-95fdfba8ea81",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_industrial_hub.jpg?alt=media&token=ef70bd73-20b6-4c57-a9a1-e44a4dcc11c9",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/royal_landmark.jpg?alt=media&token=a52151d5-3285-4e23-b7e6-78b4b975ebcd",
                "608/A, Pinnacle Business Park,\n" +
                        "Corporate Road, Near Prahladnagar Garden,\n" +
                        "Ahmedabad - 380015.",
                "Ahmedabad",
                "Gujarat",
                "380015",
                "0.0"
            )
        )

        viewModel.addBusinessDetail(arrayList)
        globalClass.log(TAG, "Business detail added...")
    }

    //todo addPeople
    suspend fun addPeopleDetail() {

        val arrayList = ArrayList<PeopleListModel>()
        arrayList.add(
            PeopleListModel(
                0,
                "Ram Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "A+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fram.jpg?alt=media&token=1575add3-82d3-4f20-8a3b-c118e15343a4",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Jeet Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "O+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fjeet.jpg?alt=media&token=ac505bf2-6aaa-4a14-b405-981698650fa1",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Sonal Patel",
                "9874578372",
                "1996-10-12",
                "Female",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsonal.jpg?alt=media&token=5bdabe59-6686-4d03-86ad-67308d209cf4",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Gaurav Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fgaurav.jpg?alt=media&token=465c1736-2360-4bbe-b635-0cb48e2874e9",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Yagna Patel",
                "9874578372",
                "1996-10-12",
                "Female",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fyagna.jpg?alt=media&token=aa70b909-c3b2-4c17-8571-631cc30870f7",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        //--
        arrayList.add(
            PeopleListModel(
                0,
                "Ram Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "A+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fram.jpg?alt=media&token=1575add3-82d3-4f20-8a3b-c118e15343a4",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Jeet Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "O+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fjeet.jpg?alt=media&token=ac505bf2-6aaa-4a14-b405-981698650fa1",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Sonal Patel",
                "9874578372",
                "1996-10-12",
                "Female",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsonal.jpg?alt=media&token=5bdabe59-6686-4d03-86ad-67308d209cf4",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Gaurav Patel",
                "9874578372",
                "1996-10-12",
                "Male",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fgaurav.jpg?alt=media&token=465c1736-2360-4bbe-b635-0cb48e2874e9",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        arrayList.add(
            PeopleListModel(
                0,
                "Yagna Patel",
                "9874578372",
                "1996-10-12",
                "Female",
                "B+",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fyagna.jpg?alt=media&token=aa70b909-c3b2-4c17-8571-631cc30870f7",
                "C-29, Jagan Park Custom road, Chala, Vapi.",
                "Vapi",
                "Gujarat",
                "396191",
                "0.0",
                "Mahesh Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Ffather.jpg?alt=media&token=6ed81725-6a1a-44b5-bd0c-aa2e537ae5f2",
                "Sheetal Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fmom.jpg?alt=media&token=2d61e9bf-77b0-4cfe-aae1-aa3ffe7e63ad",
                "Seema Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fsister.png?alt=media&token=492588a9-f573-4b03-8c08-bf7e05c5dde2",
                "Jigar Patel",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/people%2Fbrother.jpg?alt=media&token=dce92245-c72c-4c0b-9ddb-7cb262126a5d",
            )
        )

        viewModel.addPeopleDetail(arrayList)
        globalClass.log(TAG, "People detail added...")
    }

    //todo addFeed
    suspend fun addFeedDetail() {

        val arrayList = ArrayList<FeedModel>()
        arrayList.add(
            FeedModel(
                0,
                "Diwali",
                resources.getString(R.string.diwali_detail),
                "2021-11-04",
                "Vapi",
                "Gujarat",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fd1.png?alt=media&token=51f08b1a-584f-4650-8b5e-8e0ac8a230bb",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fd2.png?alt=media&token=06ca17fa-8803-4842-8e29-a55ad6634d1d",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fd3.png?alt=media&token=6f8b7225-22e5-4c8d-8e52-4ee2f6255972",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fd4.png?alt=media&token=27a76d52-a7d7-4eeb-909c-b0188e427a50",
                125,
                64
            )
        )
        arrayList.add(
            FeedModel(
                0,
                "Holi",
                resources.getString(R.string.holi_detail),
                "2020-08-24",
                "Surat",
                "Gujarat",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fh1.png?alt=media&token=9e558456-6d39-4295-bdcb-a46293b08c90",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fh2.jpg?alt=media&token=5a4a8214-d08a-4035-88e4-62982c2de2f7",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fh3.jpg?alt=media&token=8a50327c-e9ce-42a1-ba00-7622165258eb",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fh4.jpg?alt=media&token=d3f01fbf-914f-4230-80a8-4e90e49fb8ed",
                75,
                62
            )
        )
        arrayList.add(
            FeedModel(
                0,
                "Uttarayan",
                resources.getString(R.string.uttrayan_detail),
                "2020-01-14",
                "Valsad",
                "Gujarat",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fu1.png?alt=media&token=23798ee9-59ea-428e-bca7-32392c8bdb21",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fu2.jpg?alt=media&token=1fdd1e0a-faff-4963-a595-80dd4ae9fc1e",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fu3.jpeg?alt=media&token=4de415d9-eb65-4d3f-be10-822a25ae4e23",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fu4.png?alt=media&token=c0912fc9-c33c-4974-a1e0-cd2342f01ee9",
                202,
                154
            )
        )
        arrayList.add(
            FeedModel(
                0,
                "Janmashtami",
                resources.getString(R.string.janmashtami_detail),
                "2020-08-30",
                "Daman",
                "Gujarat",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fjc1.png?alt=media&token=579c7003-21e5-48ea-b407-48824084dfb7",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj2.jpg?alt=media&token=549479ac-584f-4c4c-ab12-ebc186d82c9d",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj3.jpg?alt=media&token=9471e6aa-3c21-418b-abed-a0551f1fee51",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fjc4.jpg?alt=media&token=f6ad9b58-75a1-4076-9ed7-e171449c5ef9",
                112,
                76
            )
        )
        arrayList.add(
            FeedModel(
                0,
                "Ganesh chaturthi",
                resources.getString(R.string.ganesh_chaturthi_detail),
                "2020-10-10",
                "Udwada",
                "Gujarat",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj1.png?alt=media&token=dca7ac4b-2475-435a-b8e8-9227cccf7854",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj2.jpg?alt=media&token=549479ac-584f-4c4c-ab12-ebc186d82c9d",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj3.jpg?alt=media&token=9471e6aa-3c21-418b-abed-a0551f1fee51",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/feed%2Fj4.jpg?alt=media&token=ed0a55c5-00c6-4ad5-b640-16802547655e",
                54,
                32
            )
        )

        viewModel.addFeedDetail(arrayList)
        globalClass.log(TAG, "Feed detail added...")
    }

    //todo addmatrimony
    suspend fun addMatrimonyUserDetail() {

        val arrayList = ArrayList<MatrimonyUserDetailModel>()

        arrayList.add(
            MatrimonyUserDetailModel(
            0,
            "Nirali Desai",
            "8593828921",
            "26",
            "Female",
            "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg1.1.jpg?alt=media&token=6f671ac6-0c2c-48c1-91ec-92e1e137b8d4",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg1.2.png?alt=media&token=c7df0abf-ce44-4002-81d1-bb6980e36627",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg1.3.png?alt=media&token=3b697674-cd47-44d6-abe7-077797a53edf",
                "MBA",
                "Secretary",
                "Gujarati",
                "Hindu",
                "Patel",
                "Not Married",
                "5.7",
                "A+",
                "Valsad",
                "Mumbai",
                "Maharashtra",

        )
        )

        arrayList.add(
            MatrimonyUserDetailModel(
            0,
            "Yogesh Soni",
            "7631887733",
            "27",
            "Male",
"https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb1.3.png?alt=media&token=2002c713-f134-43e7-9758-fa25c1bb75e8",
"https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb1.2.png?alt=media&token=bad08bea-eed6-47d5-8700-8c79dbd1fd9f",
"https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb1.1.png?alt=media&token=8e7b2bb7-2c05-45e3-b6ae-851913589d1a",
                "B.Tech",
                "Senior Engineer",
                "Gujarati",
                "Hindu",
                "Patel",
                "Not Married",
                "5.8",
                "B+",
                "Surat",
                "Rajkot",
                "Gujarat",

        )
        )

        arrayList.add(
            MatrimonyUserDetailModel(
            0,
            "Meena Patel",
            "8877443322",
            "26",
            "Female",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg2.3.png?alt=media&token=f1a39e9f-2dcd-4a6d-850d-c119ed5d5089",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg2.2.png?alt=media&token=56e458c4-fc4f-45a1-ba09-a75d0a22f6d8",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FgirlImages%2Fmg2.1.png?alt=media&token=2c54c438-0b08-4c8d-a96e-5d3dad2a473e",
                "MBBS",
                "Doctor",
                "Gujarati",
                "Hindu",
                "Patel",
                "Not Married",
                "5.9",
                "B+",
                "Navsari",
                "Bhuj",
                "Gujarat",

        )
        )

        arrayList.add(
            MatrimonyUserDetailModel(
                0,
                "Rahul Surani",
                "8844993214",
                "28",
                "Male",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb2.2.jpg?alt=media&token=3306e783-d424-4f3d-ae01-87df74190fbf",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb2.3.png?alt=media&token=283c4a56-c1a1-48a7-8277-70117a11c575",
                "https://firebasestorage.googleapis.com/v0/b/goodwill-book.appspot.com/o/matrimony%2FboyImages%2Fmb2.1.png?alt=media&token=593f2b5a-77ee-4b2b-a64d-9aa5e8c91c39",
                "MCA",
                "Software Engineer",
                "Gujarati",
                "Hindu",
                "Patel",
                "Not Married",
                "5.7",
                "O+",
                "Ahmedabad",
                "Vapi",
                "Gujarat",
                )
        )

        viewModel.addMatrimonyUserDetail(arrayList)
        globalClass.log(TAG, "Matrimony detail added...")
    }

    fun nextScreen() {

        lifecycleScope.launch(Dispatchers.Main) {
            Handler(Looper.myLooper()!!).postDelayed(
                {
                    findNavController().navigate(R.id.action_setUpPageFrag_to_homeFrag)
                }, setUpPageTime
            )
        }
    }
}