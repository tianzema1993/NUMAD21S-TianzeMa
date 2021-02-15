package edu.neu.madcourse.numad21s_tianzema;

public class LinkCard implements ItemClickListener {
    private String name;
    private String url;
    private boolean editable = true;

    public LinkCard() {
    }

    public LinkCard(String name, String url, boolean editable) {
        this.name = name;
        this.url = url;
        this.editable = editable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public void addItemButtonClick(int position, String name, String url) {
        if (editable) {
            editable = false;
            setName(name);
            setUrl(url);
        }
    }
}
