package com.example.eshfeenygraduationproject.eshfeeny.productsAdapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.cart.CartResponse
import com.example.domain.entity.product.ProductResponse
import com.example.domain.entity.product.ProductResponseItem
import com.example.domain.entity.patchRequestVar.PatchProductId
import com.example.eshfeenygraduationproject.R
import com.example.eshfeenygraduationproject.databinding.MedicineItemHomeBinding
import com.example.eshfeenygraduationproject.eshfeeny.home.HomeFragmentDirections
import com.example.eshfeenygraduationproject.eshfeeny.util.loadUrl
import com.example.eshfeenygraduationproject.eshfeeny.publicViewModel.ProductViewModel


class ProductHomeAdapter(
    private val viewModel: ProductViewModel,
    val userId: String,
    val favoriteProducts: ProductResponse,
    val cartProducts: CartResponse
) :
    ListAdapter<ProductResponseItem, ProductHomeAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MedicineItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: MedicineItemHomeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var isFavorite = false
        private var itemCount: Int = 0

        fun bind(product: ProductResponseItem) {
            setData2UI(product)
            addItemToCart(product)
            setFavoriteItem(product)
            navigate2Details(product)
            incrementProductAmount(product)
            decrementProductAmount(product)
        }

        private fun setData2UI(category: ProductResponseItem) {
            itemBinding.medicineNameIdTv.text = category.nameAr
            itemBinding.priceMedicineIdTv.text = "${category.price.toInt().toString()} جنيه  "
            itemBinding.imgVMedicineId.loadUrl(category.images[0])
        }

        private fun addItemToCart(category: ProductResponseItem) {

            itemBinding.add2CartBtn.setOnClickListener {

                itemCount = getQuantityInCart(cartProducts, category._id)

                itemBinding.add2CartBtn.visibility = View.GONE
                itemBinding.cardFunctionalityLayout.visibility = View.VISIBLE

                if (itemCount != 0) {
                    itemBinding.productAmount.text = itemCount.toString()
                } else {
                    itemCount++
                    viewModel.addProductToCart(userId, PatchProductId(category._id))
                    itemBinding.productAmount.text = itemCount.toString()
                }
            }
        }

        private fun setFavoriteItem(category: ProductResponseItem) {
            if (favoriteProducts.contains(category)) {
                isFavorite = true
                itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
            }
            itemBinding.heartIconId.setOnClickListener {
                if (isFavorite) {
                    viewModel.deleteFavoriteProduct(userId, category._id)
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_notfill)
                } else {
                    viewModel.addMedicineToFavorites(
                        userId,
                        PatchProductId(category._id)
                    )
                    itemBinding.heartIconId.setImageResource(R.drawable.favorite_fill)
                }
                isFavorite = !isFavorite
            }
        }

        private fun navigate2Details(product: ProductResponseItem) {
            itemBinding.imgVMedicineId.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(product._id)
                it.findNavController().navigate(action)
            }
        }

        private fun incrementProductAmount(product: ProductResponseItem) {
            itemBinding.increaseBtnId.setOnClickListener {
                itemCount++
                itemBinding.productAmount.text = itemCount.toString()
                viewModel.incrementProductNumberInCart(userId, product._id)
            }
        }

        private fun decrementProductAmount(product: ProductResponseItem) {
            itemBinding.decreaseBtnId.setOnClickListener {
                if (itemCount == 1) {

                    viewModel.removeProductFromCart(userId, PatchProductId(product._id))
                    itemCount--

                    itemBinding.add2CartBtn.visibility = View.VISIBLE
                    itemBinding.cardFunctionalityLayout.visibility = View.GONE
                } else {
                    viewModel.decrementProductNumberInCart(userId, product._id)
                    itemCount--
                    itemBinding.productAmount.text = itemCount.toString()
                }
            }
        }

        private fun getQuantityInCart(cartResponse: CartResponse, productId: String): Int {
            for (cartItem in cartResponse.cart) {
                if (cartItem.product._id == productId) {
                    return cartItem.quantity
                }
            }
            return 0
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}