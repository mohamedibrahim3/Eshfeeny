package com.example.eshfeenygraduationproject.eshfeeny.searchForProducts

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.data.local.db.user.model.UserInfo
import com.example.data.repository.ProductRepoImpl
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.FragmentProductCategoryBinding
import com.example.eshfeenygraduationproject.eshfeeny.productsAdapter.ProductCategoryAdapter
import com.example.eshfeenygraduationproject.eshfeeny.util.MedicinsCategories
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModelFactory
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.UserViewModel
import com.google.android.material.chip.Chip

class ProductCategoryFragment : Fragment() {

    private var selectedChip: Chip? = null

    private var binding: FragmentProductCategoryBinding? = null

    private val args: ProductCategoryFragmentArgs by navArgs()

    private lateinit var productViewModel: ProductViewModel
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductCategoryBinding.inflate(inflater)

        binding?.medicineRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        setupViewModel()
        navigate2HomeFragment()

        var cartProducts: CartResponse
        var favoriteProducts: ProductResponse

        userViewModel.userData.observe(viewLifecycleOwner) { userData ->

            productViewModel.getUserCartItems(userData._id)
            productViewModel.cartItems.observe(viewLifecycleOwner) { cartProductsResponse ->
                cartProductsResponse?.let { notNullCartProducts ->
                    cartProducts = notNullCartProducts

                    productViewModel.getFavoriteProducts(userData._id)
                    productViewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProductsResponse ->
                        favoriteProductsResponse?.let { notNullFavoriteProducts ->
                            favoriteProducts = notNullFavoriteProducts

                            setCategory(userData, favoriteProducts, cartProducts)
                        }
                    }
                }
            }
        }

        return binding?.root
    }

    private fun setCategory(
        userData: UserInfo,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ) {
        when (args.category) {
            "allMeds" -> {
                binding?.categoryTitle?.text = "كل الادوية"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.allMedicines,
                    "الأدوية"
                )
            }

            "dentalCare" -> {
                binding?.categoryTitle?.text = "العناية بالاسنان"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.dentalCare,
                    "العناية بالاسنان"
                )
            }

            "menProducts" -> {
                binding?.categoryTitle?.text = "منتجات الرجال"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.menProducts,
                    "منتجات الرجال"
                )
            }

            "womenProducts" -> {
                binding?.categoryTitle?.text = "منتجات المرأة"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.womenProducts,
                    "منتجات المرأة"
                )
            }

            "motherAndChild" -> {
                binding?.categoryTitle?.text = "الأم و الطفل"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.motherAndChild,
                    "الأم و الطفل"
                )
            }

            "virusProtection" -> {
                binding?.categoryTitle?.text = "الحماية من الفيروسات"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.virusProtection,
                    "الحمايه من الفيروسات"
                )
            }

            "skinAndHairCare" -> {
                binding?.categoryTitle?.text = "العناية بالبشرة و الشعر"
                setChipSearch(
                    userData._id,
                    favoriteProducts,
                    cartProducts,
                    MedicinsCategories.skinAndHair,
                    "العناية بالبشرة و الشعر"
                )
            }
        }
    }

    private fun navigate2HomeFragment() {
        binding?.backBtn?.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_medicineCategoryFragment_to_homeFragment2)
        }
    }

    private fun setupViewModel() {
        val repo = ProductRepoImpl()
        val viewModelFactory = ProductViewModelFactory(repo)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun setChipSearch(
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse,
        productCategories: List<Int>,
        productType: String
    ) {
        var isFirstChip = true
        for (med in productCategories) {
            val newChip = createChip(getString(med), userId, favoriteProducts, cartProducts)
            binding?.medicineChipGroup?.addView(newChip)

            if (isFirstChip) {
                newChip.isSelected = true
                setChipColors(newChip)

                selectedChip = newChip
                isFirstChip = false

                productViewModel.getProductType(productType)
                productViewModel.allTypeProducts.observe(viewLifecycleOwner) { response ->

                    val adapter = ProductCategoryAdapter(
                        productViewModel,
                        userId,
                        favoriteProducts,
                        cartProducts
                    )

                    adapter.submitList(response)
                    binding?.medicineRecyclerView?.adapter = adapter
                }
            }
        }
    }

    private fun createChip(
        name: String,
        userId: String,
        favoriteProducts: ProductResponse,
        cartProducts: CartResponse
    ): Chip {
        val chip = Chip(context)
        chip.text = name

        setChipColors(chip)

        chip.setOnClickListener {
            if (selectedChip != it) {
                // Deselect the previously selected chip
                selectedChip?.let { previousSelectedChip ->

                    previousSelectedChip.isSelected = false
                    setChipColors(previousSelectedChip)
                }

                chip.isSelected = true
                setChipColors(chip)

                selectedChip = chip

                val adapter = ProductCategoryAdapter(
                    productViewModel,
                    userId,
                    favoriteProducts,
                    cartProducts
                )

                val chipTypes = setOf(
                    getString(R.string.allMedicines),
                    getString(R.string.allDentalCare),
                    getString(R.string.allMenProducts),
                    getString(R.string.allWomenProducts),
                    getString(R.string.allMotherAndChild),
                    getString(R.string.allVirusProtection),
                    getString(R.string.allSkinAndHairProducts)
                )

                if (isChipNameType(name, chipTypes)) {
                    val typeName: String = setTypeName(name)
                    productViewModel.getProductType(typeName)
                    productViewModel.allTypeProducts.observe(viewLifecycleOwner) { response ->
                        adapter.submitList(response)
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                } else {
                    productViewModel.getProductsFromRemote(name)
                    productViewModel.remoteProducts.observe(viewLifecycleOwner) { response ->
                        adapter.submitList(response.body())
                        binding?.medicineRecyclerView?.adapter = adapter
                    }
                }
            }
        }
        return chip
    }

    private fun setTypeName(name: String): String {
        return when (name) {
            getString(R.string.allMedicines) -> "الأدوية"
            getString(R.string.allDentalCare) -> "العناية بالاسنان"
            getString(R.string.allMenProducts) -> "منتجات الرجال"
            getString(R.string.allWomenProducts) -> "منتجات المرأة"
            getString(R.string.allMotherAndChild) -> "الأم و الطفل"
            getString(R.string.allVirusProtection) -> "الحمايه من الفيروسات"

            else -> "العناية بالبشرة و الشعر"
        }
    }

    private fun isChipNameType(searchName: String, chipTypes: Set<String>): Boolean {
        return chipTypes.contains(searchName)
    }

    private fun setChipColors(chip: Chip) {
        val textColor = if (chip.isSelected) R.color.blue_main else R.color.text_color
        val bgColor = if (chip.isSelected) R.color.chips_color2 else R.color.chips_color
        val strokeColor =
            if (chip.isSelected) R.color.chip_stroke_selected else R.color.chip_stroke_notSelected

        chip.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), bgColor))
        chip.chipStrokeColor =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), strokeColor))

        chip.chipStrokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            context?.resources?.displayMetrics
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}