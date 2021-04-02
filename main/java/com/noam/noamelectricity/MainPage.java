package com.noam.noamelectricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Random;


public class MainPage extends Fragment {

    /*
    * --------------------
    * | System functions |
    * --------------------
    * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        * onCreateView
        * --------------
        * Being called when:
        * 1) The app is first up - before onViewCreated
        * 2) When you rotate the screen - before onViewCreated
        * 3) When you come back to this activity, from a different activity - and onViewCreated IS NOT being called after it
        * */
        if (savedInstanceState != null) { // After rotating the screen
            initARR(); // Without this line I would get null when coming back to this activity from another activity - a button click will fix it, but this line "does the click" for me
        }
        // Else would be whenever I open the fragment, after a different fragment was open
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        /*
         * Being called whenever you rotate the screen
         * NOT being called when you switch between activities - not when you leave the activity and not when you open it again
         * */
        outState.putInt("count1", quotes_programming_counter);
        outState.putInt("count2", quotes_general_counter);
        super.onSaveInstanceState(outState);
    }

    /*
     * -----------
     * | My code |
     * -----------
     * */

    String[][][] quotes = new String[4][500][2]; // All of the quotes - 4 categories, 500 quotes in each category, 2 creators
    static int quotes_programming_counter = 0, quotes_general_counter = 0, //  How many quotes there are - to know the limit of the random number generator
            lastRand = 0; // To make sure there aren't two same random numbers one after the other
    final int programmingCat = 0, generalCat = 1; // Just for myself, a "define" value, to indicate the category
    Random rand = new Random(); // Random number generator

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*
        * onViewCreated
        * --------------
        * Being called after onCreateView, and when:
        * 1) The app is first up
        * 2) When you rotate the screen
        * */

        /* Init the last updated & version, bottom of page */
        TextView home_info = view.findViewById(R.id.home_info);
        home_info.setText(String.format(getResources().getString(R.string.lastUpdate), getResources().getString(R.string.lastUpdateDate)));

        initARR(); // Inits the quotes - and will be later "overridden", every time the fragment is re-created

        Button button1 = view.findViewById(R.id.myButton1), button2 = view.findViewById(R.id.myButton2); // The buttons to generate a new quote
        TextView QD_quote = view.findViewById(R.id.QD_quote), QD_quoteBy = view.findViewById(R.id.QD_quoteBy); // Quote itself, and the person's name
        QD_quote.setBackgroundResource(R.drawable.border_style1); // A nice fancy border to the quote

        /* Chooses a random quote for when the app opens */
        String[] currentQuote = quotes[0][rand.nextInt(quotes_programming_counter)]; // Just for the first time, when the app is up
        String currentQuote_text = currentQuote[0], currentQuote_by = currentQuote[1]; // The generated quote

        /* String-builders just so the IDE won't annoy me and ask me to use string templates */
        QD_quote.setText(String.format(getResources().getString(R.string.quotedText_template), currentQuote_text));
        QD_quoteBy.setText(String.format("- %1$s", currentQuote_by));


        // Generating new quotes
        /*
        TODO Hello there! this line is marked because it's "todo" thing
        But this line isn't because it's just a text
        * */
        button1.setOnClickListener(v -> generateNewQuote(programmingCat, QD_quote, QD_quoteBy));
        button2.setOnClickListener(v -> generateNewQuote(generalCat, QD_quote, QD_quoteBy));
    }

    void generateNewQuote(int category, TextView quote, TextView by) {
        /*
         * generateNewQuote
         * -----------------
         * Parameters:
         *   int category: 0/1 - indicator which category is needed - 0 for programming quotes, 1 for general quote
         *   TextView quote - the TextView of the quote itself
         *   TextView by - the TextView of the person's name
         * The function generates a new random quote of the requested category and sets the TextViews' values
         * */
        int max = 0; // To know the limit of each category
        if (category == 0) {
            max = quotes_programming_counter;
        } else if (category == 1) {
            max = quotes_general_counter;
        }
        int currentRand = rand.nextInt(max);
        /* This loop is for making sure that there isn't the same random number twice */
        while (currentRand == lastRand) {
            currentRand = rand.nextInt(max);
        }
        /* Outside the loop, means the index is not twice the same */
        lastRand = currentRand;
        String[] currentQuote = quotes[category][currentRand];
        String currentQuote_text = currentQuote[0], currentQuote_by = currentQuote[1]; // The current quote
        quote.setText(String.format(getResources().getString(R.string.quotedText_template), currentQuote_text));
        by.setText(String.format("- %1$s", currentQuote_by));
    }

    void addQuote(int type, String quote, String by) {
        /*
        * addQuote
        * ----------
        * Parameters:
        *   int type: should be 0/1 - 0 for a programming quote and 1 for a general quote
        *   String quote: the quote itself
        *   String by: The person's name
        * The function saves a new quote into the quotes array list.
        *   Index 0: The quote itself
        *   Index 1: The person's name
        * The function also increases the static variable, the counter - therefore it is being reset in initARR
        * */
        if (type == programmingCat) {
            quotes[0][quotes_programming_counter][0] = quote;
            quotes[0][quotes_programming_counter][1] = by;
            quotes_programming_counter++;
        } else if (type == generalCat) {
            quotes[1][quotes_general_counter][0] = quote;
            quotes[1][quotes_general_counter][1] = by;
            quotes_general_counter++;
        }
    }

    void initARR() {
        /*
         * initARR
         * ---------
         * The function saves all of the quotes, according to the following algorithm:
         *  index 0 - category (0 - programming, 1 - general)
         *  index 1 - quote itself
         *  index 2 - the person name
         * The function is also resetting the counters because it will anyways re-count the items - every call to addQuote.
         *  Otherwise, the counter will increase each time, by the number of the last size.
         *  For example if programming_counter was 15 when the app first open (means first call to initARR), then each call to initARR
         *      would increase it by 15 - 15, 30, 45, etc..
         * The quotes are separated into groups of 5 just to make it easier to read and count
         */
        quotes_programming_counter = 0;
        quotes_general_counter = 0;

        /* Programming quotes */
        addQuote(programmingCat, "I think everybody in this country should learn how to program. Because it teaches you how to think.", "Steve Jobs");
        addQuote(programmingCat, "In some ways, programming is like painting. You start with a blank canvas and certain basic raw materials. You use a combination of science, art, and craft to determine what to do with them.", "Andrew Hunt");
        addQuote(programmingCat, "The best error message is the one that never shows up.", "Thomas Fuchs");
        addQuote(programmingCat, "Don't write better error messages, write code that doesn't need them.", "Jason C. McDonald");
        addQuote(programmingCat, "Programming isn't about what you know; it's about what you can figure out.", "Chris Pine");

        addQuote(programmingCat, "The best error message is the one that never shows up.", "Thomas Fuchs");
        addQuote(programmingCat, "Any fool can write code that a computer can understand. Good programmers write code that humans can understand.", "Martin Fowler");
        addQuote(programmingCat, "Experience is the name everyone gives to their mistakes.", "Oscar Wilde");
        addQuote(programmingCat, "Java is to JavaScript what car is to Carpet.", "Chris Heilmann");
        addQuote(programmingCat, "Sometimes it pays to stay in bed on Monday, rather than spending the rest of the week debugging Monday’s code.", "Dan Salomon");

        addQuote(programmingCat, "Code is like humour. When you have to explain it, it’s bad.", "Cory House");
        addQuote(programmingCat, "Fix the cause, not the symptom.", "Steve Maguire");
        addQuote(programmingCat, "Optimism is an occupational hazard of programming: feedback is the treatment.", "Kent Beck");
        addQuote(programmingCat, "Simplicity is the soul of efficiency.", "Austin Freeman");
        addQuote(programmingCat, "Before software can be reusable it first has to be usable.", "Ralph Johnson");

        addQuote(programmingCat, "Most good programmers do programming not because they expect to get paid or get adulation by the public, but because it is fun to program.", "Linus Torvalds");
        addQuote(programmingCat, "Intelligence is the ability to avoid doing work, yet getting the work done.", "Linus Torvalds");
        addQuote(programmingCat, "Any program is only as good as it is useful.", "Linus Torvalds");
        addQuote(programmingCat, "Design is a funny word. Some people think design means how it looks. But of course, if you dig deeper, it's really how it works.", "Steve Jobs");

        /* General quotes */

        addQuote(generalCat, "We cannot solve our problems with the same thinking we used when we created them.", "Albert Einstein");
        addQuote(generalCat, "A person who never made a mistake never tried anything new.", "Albert Einstein");
        addQuote(generalCat, "If you can't explain it simply, you don't understand it well enough.", "Albert Einstein");
        addQuote(generalCat, "Learn from yesterday, live for today, hope for tomorrow. The important thing is not to stop questioning.", "Albert Einstein");
        addQuote(generalCat, "Look deep into nature, and then you will understand everything better.", "Albert Einstein");

        addQuote(generalCat, "Life is like riding a bicycle. To keep your balance, you must keep moving.", "Albert Einstein");
        addQuote(generalCat, "Only two things are infinite, the universe and human stupidity, and I'm not sure about the former.", "Albert Einstein");
        addQuote(generalCat, "Logic will get you from A to B. Imagination will take you everywhere.", "Albert Einstein");
        addQuote(generalCat, "I have no special talent. I am only passionately curious.", "Albert Einstein");
        addQuote(generalCat, "It's not that I'm so smart, it's just that I stay with problems longer.", "Albert Einstein");

        addQuote(generalCat, "Coincidence is God's way of remaining anonymous.", "Albert Einstein");
        addQuote(generalCat, "The true sign of intelligence is not knowledge but imagination.", "Albert Einstein");
        addQuote(generalCat, "Only a life lived for others is a life worthwhile.", "Albert Einstein");
        addQuote(generalCat, "Education is what remains after one has forgotten what one has learned in school.", "Albert Einstein");
        addQuote(generalCat, "You can't blame gravity for falling in love.", "Albert Einstein");

        addQuote(generalCat, "Weakness of attitude becomes weakness of character.", "Albert Einstein");
        addQuote(generalCat, "The only source of knowledge is experience.", "Albert Einstein");
        addQuote(generalCat, "Peace cannot be kept by force; it can only be achieved by understanding.", "Albert Einstein");
        addQuote(generalCat, "Try not to become a man of success, but rather try to become a man of value.", "Albert Einstein");
        addQuote(generalCat, "Reality is merely an illusion, albeit a very persistent one.", "Albert Einstein");

        addQuote(generalCat, "Imagination is everything. It is the preview of life's coming attractions.", "Albert Einstein");
        addQuote(generalCat, "If we knew what it was we were doing, it would not be called research, would it?", "Albert Einstein");
        addQuote(generalCat, "Testing leads to failure, and failure leads to understanding.", "Burt Rutan");
        addQuote(generalCat, "In order to be irreplaceable, one must always be different.", "Coco Chanel");
        addQuote(generalCat, "Make it work, make it right, make it fast.", "Kent Beck");

        addQuote(generalCat, "If you don't like something, change it. If you can't change it, change your attitude.", "Maya Angelou");
        addQuote(generalCat, "Great things in business are never done by one person. They're done by a team of people.", "Steve Jobs");
        addQuote(generalCat, "Life is 10% what happens to you and 90% how you react to it.", "Charles R. Swindoll");
        addQuote(generalCat, "Keep your face always toward the sunshine - and shadows will fall behind you.", " Walt Whitman");
        addQuote(generalCat, "Sometimes when you innovate, you make mistakes. It is best to admit them quickly, and get on with improving your other innovations.", "Steve Jobs");

        addQuote(generalCat, "Research is what I'm doing when I don't know what I'm doing.", "Wernher von Braun");
        addQuote(generalCat, "What is research but a blind date with knowledge?", "Will Harvey");
        addQuote(generalCat, "If you steal from one author, it's plagiarism; if you steal from many, it's research.", "Wilson Mizner");
        addQuote(generalCat, "Research is creating new knowledge.", "Neil Armstrong");
        addQuote(generalCat, "The best preparation for tomorrow is doing your best today.", "H. Jackson Brown, Jr.");

        addQuote(generalCat, "The heart and soul of good writing is research; you should write not what you know but what you can find out about.", "Robert J. Sawyer");
        addQuote(generalCat, "What people actually refer to as research nowadays is really just Googling.", "Dermot Mulroney");
        addQuote(generalCat, "Research is to see what everybody else has seen, and to think what nobody else has thought.", "Albert Szent-Gyorgyi");
        addQuote(generalCat, "If you can't describe what you are doing as a process, you don't know what you're doing.", "W. Edwards Deming");
        addQuote(generalCat, "The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart.", "Helen Keller");

        addQuote(generalCat, "I hated every minute of training, but I said, 'Don't quit. Suffer now and live the rest of your life as a champion.'", "Muhammad Ali");
        addQuote(generalCat, "What lies behind you and what lies in front of you, pales in comparison to what lies inside of you.", "Ralph Waldo Emerson");
        addQuote(generalCat, "Don't judge each day by the harvest you reap but by the seeds that you plant.", "Robert Louis Stevenson");
        addQuote(generalCat, "You can't just ask customers what they want and then try to give that to them. By the time you get it built, they'll want something new.", "Steve Jobs");
        addQuote(generalCat, "Older people sit down and ask, 'What is it?' but the boy asks, 'What can I do with it?'.", "Steve Jobs");

        addQuote(generalCat, "Things don't have to change the world to be important.", "Steve Jobs");
    }
}