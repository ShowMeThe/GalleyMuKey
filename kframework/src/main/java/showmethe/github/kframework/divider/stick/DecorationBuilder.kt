package showmethe.github.kframework.divider.stick

class DecorationBuilder private constructor(){

    companion object{

        private var builder : DecorationBuilder? = null



        fun newBuilder() : DecorationBuilder {
            builder = DecorationBuilder()
            return builder!!
        }
    }



}