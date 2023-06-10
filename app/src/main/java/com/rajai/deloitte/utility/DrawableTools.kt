package com.rajai.deloitte.utility

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class DrawableTools {

    class DrawableLoader<Y : ImageView> private constructor(private val imageView: Y?) {
        private var url: String? = null
        private var byteArray: ByteArray? = null
        private var bitmap: Bitmap? = null
        private var file: File? = null
        private var uri: Uri? = null
        private var requestOptions = GLIDE_SCALE_CENTER_CROP

        @ColorRes
        private var color: Int? = null

        @DrawableRes
        private var drawableResId: Int? = null

        @DrawableRes
        private var gifResId: Int? = null

        @DrawableRes
        private var errorDrawableRes: Int? = null

        @DrawableRes
        private var placeholderDrawableRes: Int? = null
        private var onImageLoaded: OnActionDoneListener<Target<Drawable>>? = null
        private var animatedPlaceholderDrawable: AnimatedVectorDrawable? = null
        private var resizeHeight: Int? = null;
        private var resizeWidth: Int? = null;

        private var showLoadingProgress = true

        @SuppressLint("CheckResult")
        fun load(): Y? {
            imageView?.let { imageView ->

                val requestManager: RequestManager? = Glide.with(imageView)
                /**/
                errorDrawableRes?.let { requestOptions.error(it) }
                /**/
                val requestBuilder: RequestBuilder<Drawable>? = when {
                    url != null -> requestManager?.load(url)
                    file != null -> requestManager?.load(file)
                    uri != null -> requestManager?.load(uri)
                    byteArray != null -> requestManager?.load(byteArray)
                    bitmap != null -> requestManager?.load(bitmap)
                    else -> null
                }
                drawableResId?.let { drawableResId ->
                    color?.let { color ->
                        imageView.setImageDrawable(
                            getTintedDrawable(
                                imageView.context,
                                drawableResId,
                                color
                            )
                        )

                    } ?: let {
                        imageView.setImageResource(drawableResId)
                    }
                }
                gifResId?.let {
                    loadGif(it, GifDrawable.LOOP_FOREVER, imageView)
                }
                requestBuilder?.let { requestBuilder ->
                    onImageLoaded?.let { onImageLoaded ->
                        requestBuilder.listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                onImageLoaded.OnAction(null)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                onImageLoaded.OnAction(target)
                                return false
                            }
                        })
                    }

                    requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    resizeWidth?.let { resizeWidth ->
                        resizeHeight?.let { resizeHeight ->
                            requestOptions.override(resizeWidth, resizeHeight)
                        }
                    }
                    if (showLoadingProgress)
                        requestBuilder.apply(
                            requestOptions.placeholder(
                                createCircularProgressDrawable(
                                    imageView.context
                                )
                            )
                        )

                    requestBuilder.into(imageView)
                }
            }
            return imageView
        }

        fun withUrl(url: String): DrawableLoader<Y> {
            this.url = url
            return this
        }

        fun withByte(byteArray: ByteArray): DrawableLoader<Y> {
            this.byteArray = byteArray
            return this
        }

        fun withBitmap(bitmap: Bitmap): DrawableLoader<Y> {
            this.bitmap = bitmap
            return this
        }

        fun withResize(width: Int?, height: Int?): DrawableLoader<Y> {
            resizeWidth = width
            resizeHeight = height
            return this
        }

        fun withError(errorDrawableRes: Int?): DrawableLoader<Y> {
            this.errorDrawableRes = errorDrawableRes
            return this
        }

        fun withPlaceholder(placeholderDrawableRes: Int?): DrawableLoader<Y> {
            this.placeholderDrawableRes = placeholderDrawableRes
            return this
        }

        fun showLoadingProgress(show: Boolean): DrawableLoader<Y> {
            this.showLoadingProgress = show
            return this
        }

        fun withAnimatedPlaceholder(animatedPlaceholderDrawable: AnimatedVectorDrawable?): DrawableLoader<Y> {
            this.animatedPlaceholderDrawable = animatedPlaceholderDrawable
            return this
        }

        fun withFile(file: File): DrawableLoader<Y> {
            this.file = file
            return this
        }

        fun withUri(uri: Uri): DrawableLoader<Y> {
            this.uri = uri
            return this
        }

        fun withOptions(requestOptions: RequestOptions?): DrawableLoader<Y> {
            requestOptions?.let {
                this.requestOptions = it
            }
            return this
        }

        fun withTint(color: Int?): DrawableLoader<Y> {
            this.color = color
            return this
        }

        fun withDrawable(drawableResId: Int?): DrawableLoader<Y> {
            this.drawableResId = drawableResId
            return this
        }

        fun withOnLoad(onImageLoaded: OnActionDoneListener<Target<Drawable>>): DrawableLoader<Y> {
            this.onImageLoaded = onImageLoaded
            return this
        }

        fun withGif(gifResId: Int): DrawableLoader<Y> {
            this.gifResId = gifResId
            return this
        }

        companion object {

            fun <Y : ImageView> initWith(imageView: Y): DrawableLoader<*> {
                return DrawableLoader(imageView)
            }
        }
    }

    private fun base64ToBitmap(b64: String): Bitmap {
        val imageAsBytes = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    companion object {

        var GLIDE_SCALE_CENTER_INSIDE = RequestOptions.centerInsideTransform()
        var GLIDE_SCALE_CIRCLE_CROP = RequestOptions.circleCropTransform()
        var GLIDE_SCALE_CENTER_CROP = RequestOptions.centerCropTransform()
        var GLIDE_SCALE_CENTER_FIT = RequestOptions.fitCenterTransform()

        public fun createCircularProgressDrawable(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 6f
            circularProgressDrawable.centerRadius = 99f
            circularProgressDrawable.alpha = 70
            circularProgressDrawable.start();
            return circularProgressDrawable;
        }


        fun <Y : ImageView> loadGif(
            @RawRes @DrawableRes gifResId: Int,
            loopCount: Int,
            imageView: Y?,
//            afterGifLoadListener: OnActionDoneListener<View>?=null
        ) {
            imageView?.let { imageView ->
                Glide.with(imageView.context).asGif()
                    .placeholder(createCircularProgressDrawable(imageView.context)).load(gifResId)
                    .optionalFitCenter()
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .listener(object : RequestListener<GifDrawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<GifDrawable>,
                            isFirstResource: Boolean
                        ): Boolean {
//                            afterGifLoadListener?.OnAction(null)
                            return false
                        }

                        override fun onResourceReady(
                            resource: GifDrawable,
                            model: Any,
                            target: Target<GifDrawable>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource.setLoopCount(loopCount)
                            //////
                            resource.registerAnimationCallback(object :
                                Animatable2Compat.AnimationCallback() {
                                override fun onAnimationEnd(drawable: Drawable?) {
//                                    afterGifLoadListener?.OnAction(imageView)
                                }
                            })
                            return false
                        }
                    }).into(imageView)
            }
        }

        fun filePathToBase64(filePath: String?): String? {
            var bmp: Bitmap? = null
            var bos: ByteArrayOutputStream? = null
            var bt: ByteArray? = null
            var encodeString: String? = null
            try {
                bmp = BitmapFactory.decodeFile(filePath)
                bos = ByteArrayOutputStream()
                bmp?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                bt = bos.toByteArray()
                encodeString = Base64.encodeToString(bt, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return encodeString
        }

        fun getTintedDrawable(
            context: Context,
            @DrawableRes drawableResId: Int,
            @ColorRes color: Int
        ): Drawable? {
            val drawable = ContextCompat.getDrawable(context, drawableResId)
            return getTintedDrawable(context, drawable, color)
        }

        fun getTintedDrawable(
            context: Context,
            drawable: Drawable?,
            @ColorRes color: Int
        ): Drawable? {
            var mWrappedDrawable: Drawable? = null

            try {
                val drawableColor = ContextCompat.getColor(context, color)
                mWrappedDrawable = Objects.requireNonNull<Drawable>(drawable).mutate()
                mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable)
                DrawableCompat.setTint(mWrappedDrawable, drawableColor)
                DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN)
            } catch (e: Exception) {
                e.printStackTrace()

            }

            return mWrappedDrawable
        }

        fun getTintedDrawable(
            context: Context,
            @DrawableRes drawableResId: Int,
            color: String
        ): Drawable? {
            var mWrappedDrawable: Drawable? = null

            try {
                val drawableColor = Color.parseColor(color)
                val drawable = ContextCompat.getDrawable(context, drawableResId)
                mWrappedDrawable = Objects.requireNonNull<Drawable>(drawable).mutate()
                mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable!!)
                DrawableCompat.setTint(mWrappedDrawable!!, drawableColor)
                DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN)
            } catch (e: Exception) {
                e.printStackTrace()

            }
            return mWrappedDrawable
        }

        fun setImageUrl(imageView: ImageView, url: String) {
            DrawableLoader.initWith(imageView).withUrl(url).load()
        }

        fun setImageFile(imageView: ImageView, file: File) {
            DrawableLoader.initWith(imageView).withFile(file)
                .load()
        }

        fun setImageBase64(imageView: ImageView, base64: String) {
            val imageByteArray = Base64.decode(base64, Base64.DEFAULT)
            DrawableLoader.initWith(imageView).withByte(imageByteArray)
                .withOptions(GLIDE_SCALE_CENTER_FIT)
                .load()
        }

        fun setGifImg(imageView: ImageView, gifResId: Int) {
            DrawableLoader.initWith(imageView).withGif(gifResId).load()
        }
    }
}
