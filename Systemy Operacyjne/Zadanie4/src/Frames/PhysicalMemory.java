package Frames;

import Frames.Frame;

import java.util.List;

public class PhysicalMemory {
    private List<Frame> frameList;

    public PhysicalMemory(List<Frame> frameList) {
        this.frameList = frameList;
    }

    public List<Frame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<Frame> frameList) {
        this.frameList = frameList;
    }
}
